package com.oms.service;

import com.oms.dto.RequestStructure;
import com.oms.dto.Requester;
import com.oms.dto.requests.*;
import com.oms.dto.responses.PODetailAsList;
import com.oms.mapper.PODetailsMapper;
import com.oms.mapper.response.ProductOrderMapper;
import com.oms.models.POMasterEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.models.ProductShipmentManagerEntity;
import com.oms.models.repository.POMasterRepository;
import com.oms.models.repository.ProductOrderManagerRepository;
import com.oms.models.repository.ProductShipmentManagerRepository;
import com.oms.pojo.requestPojo.GetOrdersByPOIdRequest;
import com.oms.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final ProductOrderManagerRepository productOrderManagerRepository;
    private final ProductShipmentManagerRepository productShipmentManagerRepository;
    private final POMasterRepository poMasterRepository;
    private final PODetailsMapper poDetailsMapper;
    private final ProductOrderMapper productOrderMapper;
    private final ManagementService managementService;
    private final UserManagementService userManagementService;

    @Transactional
    public PODetails saveNewOrderDetails(PODetails poDetails, Requester requester) throws Exception {
        //Validate user first

        var customerDetails = poDetails.getCustomerDetailsEntity();
        var poOrders = productOrderMapper.productOrderListToProductOrderManagerEntityList(poDetails.getProductOrderManagerEntity());
        if (customerDetails == null || CollectionUtils.isEmpty(poDetails.getProductOrderManagerEntity()) /*|| poDetails.getId()!=null*/ || poDetails.getPoNumber() == null) {
            throw new Exception("Invalid request");
        }
        //IF PO ID IS NULL THEN ITS NEW ORDERS
        var po = updatePODetails(poDetails.getId(), poDetails.getPoNumber(), poDetails.getOrderStatus(), poDetails.getPoDate(), customerDetails, poDetails.getTotalAmount(), requester);
        poOrders.forEach(
                order ->
                {
                    order.setPoId(po.getId());
                    order.setCustomerId(customerDetails.getId());
                    if (order.getId() == null) {
                        //If order not exist use same object
                        order.setCreatedBy(requester.getUserId());
                    } else {
                        //If order already exist
                        var orderPersisted = productOrderManagerRepository.findById(order.getId());
                        if (orderPersisted.isPresent()) {
                            var test = orderPersisted.get();
                            order.setPoId(test.getPoId());
                            order.setCreatedDate(test.getCreatedDate());
                            order.setCreatedBy(test.getCreatedBy());
                            order.setModifiedBy((requester.getUserId()));
                            order.getProductShipmentDetails().forEach(x -> {
                                x.setCreatedBy(x.getCreatedBy() == null || x.getCreatedBy() == 0 ? requester.getUserId() : x.getCreatedBy());
                                x.setModifiedBy(x.getId() != null ? requester.getUserId() : null);
                            });
                        }
                    }
                }
        );
        var savedOrders = productOrderManagerRepository.saveAll(poOrders);
        poDetails.setProductOrderManagerEntity(productOrderMapper.productOrderEntityListToProductOrderManagerList(savedOrders));
        poDetails.setId(po.getId());
        return poDetails;
    }

    @Transactional
    public String updateSchedules(Requester requester, ScheduleUpdateRequest request) {
        var orderPersisted = productOrderManagerRepository.findById(request.getProductOrderId());

        if (orderPersisted.isPresent()) {
            var test = orderPersisted.get();
//            orderPersisted.get().setPoId(test.getPoId());
//            orderPersisted.get().setCreatedDate(test.getCreatedDate());
//            orderPersisted.get().setCreatedBy(test.getCreatedBy());
            orderPersisted.get().setModifiedBy(requester.getUserId());

            if (request.getProductShipmentManager().getId() != null) {
                var schedule = productShipmentManagerRepository.getById(request.getProductShipmentManager().getId());
                schedule.setScheduleQty(request.getProductShipmentManager().getScheduleQty());
                schedule.setSuppliedQty(request.getProductShipmentManager().getSuppliedQty());
                schedule.setPendingQty(request.getProductShipmentManager().getPendingQty());
                schedule.setEsplPO(request.getProductShipmentManager().getEsplPO());
                schedule.setInvoiceNo(request.getProductShipmentManager().getInvoiceNo());
                schedule.setInvoiceDate(request.getProductShipmentManager().getInvoiceDate());
                schedule.setSupplierDeliveryDate(request.getProductShipmentManager().getSupplierDeliveryDate());
                schedule.setModifiedBy(requester.getUserId());
                productShipmentManagerRepository.save(schedule);
            } else {
                request.getProductShipmentManager().setCreatedBy(String.valueOf(requester.getUserId()));
                orderPersisted.get().getProductShipmentDetails().add(productOrderMapper.ProductShipmentToProductShipmentManagerEntity(request.getProductShipmentManager()));
                var savedOrders = productOrderManagerRepository.save(orderPersisted.get());
            }

        }


        return "success";
    }

    /**
     * tHIS METHOD IS USED TO DELETE iTEM FROM PURCHASE ORDER - IT WILL DELETE ITEM AS WELL AS IT'S SCHEDULE FROM TABLE
     *
     * @param productOrderId
     * @param requester
     * @return
     * @throws Exception
     */
    public String deleteItemOrderFromPO(Long productOrderId, Requester requester) throws Exception {
        validateUserAccessForProductOrder(requester.getUserId(), productOrderId);
        if (productOrderManagerRepository.existsById(productOrderId)) {
            productOrderManagerRepository.deleteById(productOrderId);
        } else {
            throw new Exception("Not found this item / not accessible.");
        }
        return "success";
    }

    /**
     * This Method Used to create a new Purchase order / update existing purchase order.
     *
     * @param poId
     * @param poNumber
     * @param status
     * @param poDate
     * @param customer
     * @param totalAmount
     * @param requester
     * @return
     * @throws Exception
     */
    private POMasterEntity updatePODetails(Long poId, String poNumber, String status, Date poDate,
                                           Customer customer, double totalAmount, Requester requester) throws Exception {
        POMasterEntity po;
        if (poId == null) {
            po = new POMasterEntity();
            po.setCreatedBy(requester.getUserId());
        } else {
            //Add check while update operation if modifier is same as creator of PO or supervisor/manager
            po = poMasterRepository.getById(poId);
            po.setModifiedBy(requester.getUserId());
            //validate user
            validateUserAccess(requester.getUserId(), poId);
        }
        po.setOrderStatus(status);
        po.setPoDate(poDate);
        po.setPoNumber(poNumber.trim());
        po.setCustomerId(customer.getId());
        po.setTotalAmount(totalAmount);//this may be not required but will keep - as of now
        return poMasterRepository.save(po);
    }

    public PODetails getPurchaseOrderById(GetOrdersByPOIdRequest request, Requester requester) throws Exception {

        validateUserAccess(requester.getUserId(), request.getPoId());
        var poEntity = poMasterRepository.findById(request.getPoId());
        if (poEntity.isPresent()) {
            poEntity.get().getProductOrderManagerEntity().forEach(order -> order.getProductShipmentDetails().sort(Comparator.comparing(ProductShipmentManagerEntity::getCustomerRequestedDate)));
            return poDetailsMapper.poDetailsPOJOMapper(poEntity.get());
        } else {
            throw new Exception("Record not found !!");
        }

    }

    /**
     * This method is used to return po records only to populate admin table without item details
     *
     * @param request
     * @param requester
     * @return
     * @throws Exception
     */
    public List<PODetailAsList> getAllPurchaseOrder(ReportsFilterRequest request, Requester requester) throws Exception {
        if (!userManagementService.validateUser(requester.getUserId())) throw new Exception("Invalid user request!");
        List<PODetailAsList> result = new ArrayList<>();
        Specification<POMasterEntity> test = Specification.where(null);
        //IF filtered with customer
        if (request.getCustomerId() != null && request.getCustomerId() != 0) {
            test = test.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerId"), request.getCustomerId()));
        }
        //IF filtered with fromDate and to date
        if (request.getFromDate() != null && request.getToDate() != null) {
            if (request.getFromDate().after(request.getToDate()))
                throw new Exception("From Date can not be greater that to date..");
            test = test.and((r, q, c) -> c.between(r.get("poDate"), request.getFromDate(), request.getToDate()));
        }
        test = test.and((r, q, c) -> r.get("orderStatus").in(Constants.POStatus.getStatusList(request.getStatus() < 5 ? request.getStatus() : 1)));
        var userAccessList = userManagementService.getTeamMemberList(requester.getUserId());
        test = test.and((r, q, c) -> r.get("createdBy").in(userAccessList));
        var x = poMasterRepository.findAll(test);
        if (request.getStatus() == 5) {
            x.forEach(po ->
            {
                po.getProductOrderManagerEntity().forEach(order -> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment ->
                        shipment.getSupplierDeliveryDate() == null).collect(Collectors.toList())));
                //  entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
            });
        } else if (request.getStatus() == 6) {
            x.forEach(po ->
                            po.getProductOrderManagerEntity().forEach(order -> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment ->
                                    shipment.getSupplierDeliveryDate() != null && (shipment.getInvoiceDate() == null || "".equals(shipment.getInvoiceNo()))).collect(Collectors.toList())))
                    //entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
            );
        }

        x.forEach(p -> result.add(new PODetailAsList(p.getId(), p.getPoNumber(), p.getPoDate(), p.getOrderStatus(), p.getTotalAmount(), p.getCustomerId(), p.getCustomerDetailsEntity().getCustomerName(), p.getCreatedBy(), p.getCreatedDate(), p.getModifiedDate(), p.getPoDocumentName())));
        result.forEach(m -> m.setCreatedBy(managementService.getFirstNameAndLastName(Long.parseLong(m.getCreatedBy()))));
        result.sort(Comparator.comparing(PODetailAsList::getCreatedDate));
        return result;
    }

    public String updateStatus(Long poId, Requester requester, String requestedStatus) throws Exception {
        String status;
        switch (requestedStatus) {
            case "cancel":
                status = Constants.POStatus.CANCEL_PO;
                break;
            case "amend":
                status = Constants.POStatus.AMENDED_PO;
                break;
            case "complete":
                status = Constants.POStatus.COMPLETED_PO;
                break;
            case "active":
                status = Constants.POStatus.ACTIVE_PO;
                break;
            default:
                throw new Exception("InvalidRequest");
        }
        if (poMasterRepository.existsById(poId)) {
            var po = poMasterRepository.getById(poId);
            validateUserAccess(requester.getUserId(), poId);
            po.setOrderStatus(status);
            poMasterRepository.save(po);
            return "Status Updated";
        } else {
            throw new Exception("PO does not exist");
        }
    }

    public List<PODetails> getFilteredReport(RequestStructure<FilteredReportRequest> request) {
        var req = request.getRequest();
        List<POMasterEntity> poMasterEntities = new ArrayList<>();
        var statusList = req.getStatus() == null || req.getStatus().size() == 0 ? Constants.POStatus.getStatusList(5) : req.getStatus();
        if (req.getCustomer() != null && req.getMfgItem() != null) {
            poMasterEntities = poMasterRepository.findByOrderStatusInAndCustomerIdAndProductOrderManagerEntity_ProductId(statusList, req.getCustomer(), req.getMfgItem());
        } else if (req.getCustomer() != null) {
            poMasterEntities = poMasterRepository.findByOrderStatusInAndCustomerId(statusList, req.getCustomer());
        } else if (req.getMfgItem() != null) {
            poMasterEntities = poMasterRepository.findByOrderStatusInAndProductOrderManagerEntity_ProductId(statusList, req.getMfgItem());
        } else if (req.getManufacturer() != null) {
            //   will add logic in some time
        }
        List<PODetails> responsePOList = new ArrayList<>();
        poMasterEntities.forEach(x -> responsePOList.add(poDetailsMapper.poDetailsPOJOMapper(x)));
        return responsePOList;
    }

    public List<ProductOrderManager> bulkSupplierDeliveryDateUpdate(RequestStructure<BulkSupplierDeliveryDateUpdateRequest> requestPayload) throws Exception {
        validateUserAccessForProductOrder(requestPayload.getRequester().getUserId(), requestPayload.getRequest().getProductOrderId());
        var request = requestPayload.getRequest();
  /*  //Given - total quantity to deliver,date to deliver
    //Pick 1st schedule
        // subtract total quantity by scheduled quantity and add SDD
    //Case 1 : If scheduled quantity is greater than total quantity
        // update scheduled quantity with total quantity and create new schedule for other pending schedule quantity
        //example :
       Before update :  total quantity : 100 - Item required qty was 110
                        schedule 1: scheduled qty : 60  pending QTY = 60   supplied QTY = 0
                        schedule 2: scheduled qty : 50  pending QTY = 50   supplied QTY = 0
         solution    : total quantity : 100 - Item required qty was 110
                        schedule 1: scheduled qty : 60  pending QTY = 60   supplied QTY = 0 total QTY = 100-60 = 40     |
                        schedule 2: scheduled qty : 40  pending QTY = 40   supplied QTY = 0 total QTY = 40 - 40 = 0     |update SDD and update scheduled qty from 50 to 40    |
                        schedule 3: scheduled qty : 10  pending QTY = 10   supplied QTY = 0 total QTY = 0               |SCHEDULED QTY 10 CAME FROM PREVIOUS SCHEDULE
   */
        var productOrder = productOrderManagerRepository.getById(request.getProductOrderId());
        productOrder.getProductShipmentDetails().sort(Comparator.comparing(ProductShipmentManagerEntity::getCustomerRequestedDate));
        productOrder.getProductShipmentDetails().forEach(x -> {

            if (x.getScheduleQty() <= request.getTotalDeliveryQuantity() && request.getTotalDeliveryQuantity() != 0 && (x.getSupplierDeliveryDate() == null || 0 == request.getSupplierDeliveryDate().compareTo(x.getSupplierDeliveryDate()))) {
                x.setSupplierDeliveryDate(request.getSupplierDeliveryDate());
                request.setTotalDeliveryQuantity(request.getTotalDeliveryQuantity() - x.getScheduleQty());
            } else if (x.getScheduleQty() > request.getTotalDeliveryQuantity() && request.getTotalDeliveryQuantity() != 0 && (x.getSupplierDeliveryDate() == null || 0 == request.getSupplierDeliveryDate().compareTo(x.getSupplierDeliveryDate()))) {
                var newScheduleQuantity = x.getScheduleQty() - request.getTotalDeliveryQuantity();
                var updatedScheduleQuantity = request.getTotalDeliveryQuantity(); //this will be updated in current schedule
                x.setScheduleQty(request.getTotalDeliveryQuantity());
                x.setPendingQty(request.getTotalDeliveryQuantity());
                x.setSupplierDeliveryDate(request.getSupplierDeliveryDate());
                x.setSuppliedQty(0L);
                var newSchedule = new ProductShipmentManagerEntity();
                newSchedule.setScheduleQty(newScheduleQuantity);
                newSchedule.setProductOrderId(x.getProductOrderId());
                newSchedule.setSuppliedQty(0L);
                newSchedule.setPendingQty(newScheduleQuantity);
                newSchedule.setCustomerRequestedDate(x.getCustomerRequestedDate());
                newSchedule.setCreatedBy(requestPayload.getRequester().getUserId());
                newSchedule.setRemarks("Created from scheduleID : " + x.getId());
                productShipmentManagerRepository.save(newSchedule);
                request.setTotalDeliveryQuantity(request.getTotalDeliveryQuantity() - x.getScheduleQty());
            }
            productShipmentManagerRepository.save(x);
        });
        return null;
    }

    public List<ProductOrderManager> bulkInvoiceDetailsUpdate(RequestStructure<BulkInvoiceDetailsUpdateRequest> requestPayload) throws Exception {
        validateUserAccessForProductOrder(requestPayload.getRequester().getUserId(), requestPayload.getRequest().getProductOrderId());
        var request = requestPayload.getRequest();
          /*  //Given - total quantity to deliver,invoice number, invoice date , ESPLPO
        Case 0 : Subtract total quantity by pending quantity and update pending QTY to 0 and supplied QTY to pending QTY
            Example :
                before update: total QTY to deliver =100
                 schedule 1: pending QTY = 50   supplied QTY = 0
                 schedule 2: pending QTY = 150  supplied QTY = 0
                after update:
                 schedule 1: pending QTY = 0    supplied QTY = 50     total QTY = 100-50 =50    |Update invoice details|
                 schedule 2: pending QTY = 0    supplied QTY = 50     total QTY = 50-50 =0      |Update invoice details|
                 schedule 2: pending QTY = 100  supplied QTY = 0     total QTY = 0
   */
        var productOrder = productOrderManagerRepository.getById(request.getProductOrderId());
        productOrder.getProductShipmentDetails().sort(Comparator.comparing(ProductShipmentManagerEntity::getCustomerRequestedDate));
        productOrder.getProductShipmentDetails().forEach(x -> {

            if (x.getScheduleQty() <= request.getTotalDeliveryQuantity() && request.getTotalDeliveryQuantity() != 0 && (x.getInvoiceNo() == null || x.getInvoiceNo().trim().isEmpty() || x.getInvoiceNo().equalsIgnoreCase(request.getInvoiceNo()))) {
                x.setSupplierDeliveryDate(x.getSupplierDeliveryDate() == null ? request.getInvoiceDate() : x.getSupplierDeliveryDate());
                x.setPendingQty(0L);
                x.setSuppliedQty(x.getScheduleQty());
                x.setInvoiceNo(request.getInvoiceNo());
                x.setInvoiceDate(request.getInvoiceDate());
                x.setEsplPO(request.getEsplpo());
                x.setModifiedBy(requestPayload.getRequester().getUserId());

                request.setTotalDeliveryQuantity(request.getTotalDeliveryQuantity() - x.getScheduleQty());
            } else if (x.getScheduleQty() > request.getTotalDeliveryQuantity() && request.getTotalDeliveryQuantity() != 0 && (x.getInvoiceNo() == null || x.getInvoiceNo().trim().isEmpty() || x.getInvoiceNo().equalsIgnoreCase(request.getInvoiceNo()))) {
                var newScheduleQuantity = x.getScheduleQty() - request.getTotalDeliveryQuantity();
                var updatedScheduleQuantity = request.getTotalDeliveryQuantity(); //this will be updated in current schedule

                //x.setPendingQty(request.getQuantity());
                // x.setSuppliedQty(0L);
                var newSchedule = new ProductShipmentManagerEntity();
                newSchedule.setScheduleQty(newScheduleQuantity);
                newSchedule.setProductOrderId(x.getProductOrderId());

                newSchedule.setPendingQty(newScheduleQuantity);
                newSchedule.setCustomerRequestedDate(x.getCustomerRequestedDate());
                newSchedule.setCreatedBy(requestPayload.getRequester().getUserId());
                x.setScheduleQty(request.getTotalDeliveryQuantity());
                x.setSupplierDeliveryDate(x.getSupplierDeliveryDate() == null ? request.getInvoiceDate() : x.getSupplierDeliveryDate());
                x.setPendingQty(0L);
                x.setSuppliedQty(x.getScheduleQty());
                x.setInvoiceNo(request.getInvoiceNo());
                x.setInvoiceDate(request.getInvoiceDate());
                x.setEsplPO(request.getEsplpo());
                x.setModifiedBy(requestPayload.getRequester().getUserId());
                productShipmentManagerRepository.save(newSchedule);
                request.setTotalDeliveryQuantity(request.getTotalDeliveryQuantity() - x.getScheduleQty());
            }
            productShipmentManagerRepository.save(x);
        });

        return null;
    }

    //
    private void validateUserAccess(int userId, long poId) throws Exception {
        var purchaseOrder = poMasterRepository.getById(poId);
        var userValidation = userManagementService.validateUser(userId);
        var userAccessValidation = userId == purchaseOrder.getCreatedBy() || userManagementService.getMangerAndSupervisor(purchaseOrder.getCreatedBy()).contains(userId);
        if (!(userValidation && userAccessValidation)) throw new Exception("Invalid user access");
    }

    private void validateUserAccessForProductOrder(int userId, long productOrderId) throws Exception {
        var productOrder = productOrderManagerRepository.getById(productOrderId);
        var userValidation = userManagementService.validateUser(userId);
        var userAccessValidation = userId == productOrder.getCreatedBy() || userManagementService.getMangerAndSupervisor(productOrder.getCreatedBy()).contains(userId);
        if (!(userValidation && userAccessValidation)) throw new Exception("Invalid user access");
    }

    private void validateItemDetails(ProductOrderManagerEntity entity) {


    }
}
