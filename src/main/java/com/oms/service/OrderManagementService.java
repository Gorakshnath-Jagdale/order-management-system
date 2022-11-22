package com.oms.service;

import com.oms.dto.RequestStructure;
import com.oms.dto.Requester;
import com.oms.dto.requests.*;
import com.oms.dto.responses.PODetailAsList;
import com.oms.mapper.NewCustomerMapper;
import com.oms.mapper.PODetailsMapper;
import com.oms.mapper.ResponseMapper;
import com.oms.mapper.getAllOrderByCustomerIdAndPONumberMapper;
import com.oms.mapper.response.ProductOrderMapper;
import com.oms.models.POMasterEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.models.repository.*;
import com.oms.pojo.requestPojo.GetExcelRequest;
import com.oms.pojo.requestPojo.GetOrdersByCustomerAndPONumberRequest;
import com.oms.service.util.Constants;
import com.oms.service.util.ExcelGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final ExcelGeneratorService excelGeneratorService;
    private final ProductOrderManagerRepository productOrderManagerRepository;
    private final ProductShipmentManagerRepository productShipmentManagerRepository;
    private final POMasterRepository poMasterRepository;
    private final PODetailsMapper poDetailsMapper;
    private final ProductOrderMapper productOrderMapper;

    @Transactional
    public PODetails saveNewOrderDetails(PODetails poDetails, Requester requester) throws Exception {
        var customerDetails = poDetails.getCustomerDetailsEntity();
        var poOrders = productOrderMapper.productOrderListToProductOrderManagerEntityList(poDetails.getProductOrderManagerEntity());
        if (customerDetails == null || CollectionUtils.isEmpty(poOrders) /*|| poDetails.getId()!=null*/ || poDetails.getPoNumber() == null) {
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
                        order.setCreatedBy(String.valueOf(requester.getUserId()));
                    } else {
                        var orderPersisted = productOrderManagerRepository.findById(order.getId());
                        if (orderPersisted.isPresent()) {
                            var test = orderPersisted.get();
                            order.setPoId(test.getPoId());
                            order.setCreatedDate(test.getCreatedDate());
                            order.setCreatedBy(test.getCreatedBy());
                            order.setModifiedBy(String.valueOf(requester.getUserId()));
                        }
                    }
                }
        );
        var savedOrders=productOrderManagerRepository.saveAll(poOrders);
        poDetails.setProductOrderManagerEntity(productOrderMapper.productOrderEntityListToProductOrderManagerList(savedOrders));
        poDetails.setId(po.getId());
        return poDetails;
    }

    @Transactional
    public String updateSchedules(Requester requester, ScheduleUpdateRequest request) {
        var orderPersisted = productOrderManagerRepository.findById(request.getProductOrderId());

                        if (orderPersisted.isPresent()) {
                            var test = orderPersisted.get();
                            orderPersisted.get().setPoId(test.getPoId());
                            orderPersisted.get().setCreatedDate(test.getCreatedDate());
                            orderPersisted.get().setCreatedBy(test.getCreatedBy());
                            orderPersisted.get().setModifiedBy(String.valueOf(requester.getUserId()));

                            if(request.getProductShipmentManager().getId()!=null)
                            {
                               var schedule= productShipmentManagerRepository.getById(request.getProductShipmentManager().getId());
                                schedule.setScheduleQty(request.getProductShipmentManager().getScheduleQty());
                                schedule.setSuppliedQty(request.getProductShipmentManager().getSuppliedQty());
                                schedule.setPendingQty(request.getProductShipmentManager().getPendingQty());
                                schedule.setEsplPO(request.getProductShipmentManager().getEsplPO());
                                schedule.setInvoiceNo(request.getProductShipmentManager().getInvoiceNo());
                                schedule.setInvoiceDate(request.getProductShipmentManager().getInvoiceDate());
                                schedule.setSupplierDeliveryDate(request.getProductShipmentManager().getSupplierDeliveryDate());
                                schedule.setPov(request.getProductShipmentManager().getPov());
                                schedule.setModifiedBy(String.valueOf(requester.getUserId()));
                                productShipmentManagerRepository.save(schedule);
                            }else
                            {
                                request.getProductShipmentManager().setCreatedBy(String.valueOf(requester.getUserId()));
                                orderPersisted.get().getProductShipmentDetails().add(productOrderMapper.ProductShipmentToProductShipmentManagerEntity(request.getProductShipmentManager()));
                                var savedOrders=productOrderManagerRepository.save(orderPersisted.get());
                            }

                        }


                    return "success";
    }


    private POMasterEntity updatePODetails(Long poId, String poNumber, String status, Date poDate,
                                           Customer customer, double totalAmount, Requester requester) {
        POMasterEntity po;
        if (poId == null) {
            po = new POMasterEntity();
            po.setUserLevel(requester.getUserLevel());
            po.setCreatedBy(String.valueOf(requester.getUserId()));
        } else {
            po = poMasterRepository.getById(poId);
            po.setModifiedBy(String.valueOf(requester.getUserId()));
        }  Calendar cal = Calendar.getInstance();
        cal.setTime(poDate);
        cal.add(Calendar.DAY_OF_MONTH,customer.getPaymentTerm());
        po.setOrderStatus(status);
        po.setPoDate(poDate);
        po.setPoNumber(poNumber.trim());
        po.setCustomerId(customer.getId());
        po.setTotalAmount(totalAmount);

        return poMasterRepository.save(po);
    }

//    private List<ProductOrderManagerEntity> SaveOrUpdateShipments(List<ProductOrderManagerEntity> orderManagerEntities) {
//        return productOrderManagerRepository.saveAll(orderManagerEntities);
//    }
//
//    @Transactional
//    public CustomerDetailsPojo updateOrderDetails(CustomerDetailsPojo customerDetails) throws Exception {
//        if (customerDetails.getId() == null) {
//            throw new Exception("ID should not be empty.");
//        } else {
//            return responseMapper.customerDetailsPojoMapper(customerDetailsRepository.save(responseMapper.customerDetailsEntityMapper(customerDetails)));
//        }
//
//    }
//
//
    public ByteArrayResource getReports(GetExcelRequest request, Requester requester) throws IOException {
        List<POMasterEntity> entities = new ArrayList<>();
        List<PODetails> entities2 = new ArrayList<>();
        if(!StringUtils.isEmpty(request.getPoNumber()))
        {
            var po=poMasterRepository.findByPoNumberIgnoreCase(request.getPoNumber());
            if(request.isGetOrdersWithEmptySDD()) {
                po.getProductOrderManagerEntity().forEach(order -> {
                            order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment ->
                                    shipment.getSupplierDeliveryDate() == null).collect(Collectors.toList()));
                        }
                );
            }
                entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));

//            List<ProductShipmentManagerEntity> finalProductShipmentsByCustomer = productShipmentsByCustomer;
//            poOrders.forEach(order-> finalProductShipmentsByCustomer.addAll(order.getProductShipmentDetails()));
//            if(!request.isGetCompleteOrders()) productShipmentsByCustomer = finalProductShipmentsByCustomer.stream().filter(x->x.getSupplierDeliveryDate()==null).collect(Collectors.toList());
        }
        else if (request.isSingleCustomer()) {

            if(request.isGetOrdersWithEmptySDD())
            {
              var    details=poMasterRepository.findByCustomerIdInAndOrderStatusInIgnoreCaseAndUserLevelOrderByPoDateDesc(request.getCustomerList(), Constants.POStatus.getStatusList(1),requester.getUserLevel());
                details.forEach(po->
                { po.getProductOrderManagerEntity().forEach(order-> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment->
                                shipment.getSupplierDeliveryDate() == null).collect(Collectors.toList())));
                                entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
                        );
            }
            else
            {
               var details=poMasterRepository.findByCustomerIdInAndOrderStatusInIgnoreCaseAndUserLevelOrderByPoDateDesc(request.getCustomerList(), Constants.POStatus.getStatusList(request.getOrderStatusCode()),requester.getUserLevel());
                details.forEach(po->entities2.add( poDetailsMapper.poDetailsPOJOMapper(po)));
            }

        }else
        {
            if(request.isGetOrdersWithEmptySDD())
            {
                var details=poMasterRepository.findByOrderStatusInIgnoreCaseAndUserLevelOrderByPoDateDesc(Constants.POStatus.getStatusList(1),requester.getUserLevel());
                details.forEach(po->
                        { po.getProductOrderManagerEntity().forEach(order-> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment->
                                shipment.getSupplierDeliveryDate() == null).collect(Collectors.toList())));
                            entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
                );
            }else
            {
                var details=poMasterRepository.findByOrderStatusInIgnoreCaseAndUserLevelOrderByPoDateDesc(Constants.POStatus.getStatusList(request.getOrderStatusCode()),requester.getUserLevel());
                details.forEach(po->entities2.add( poDetailsMapper.poDetailsPOJOMapper(po)));
            }
        }
        return excelGeneratorService.getOrderDetailsExcel(entities2, request.isSingleCustomer());
    }
//
//    public List<ProductOrderManagerPojo> getAllOrder() {
//        return getAllOrderByCustomerIdAndPONumberMapper.productOrderManagerEntityToProductOrderManagerPojoList(orderManagerRepository.findAll());
//    }
//
////    public List<OrderDetailsResponse> getAllOrderWithFilter(GetALLOrderFiltersRequest request) {
////
////       return null;//responseMapper.orderListMapper(orderManagerRepository.findByCustomerDetails_IdIsOrInvoiceNoContainingIgnoreCaseOrMfgItemNoContainingIgnoreCaseOrCustomerPartNoContainingIgnoreCaseOrPoDateBetween(request.customerId,request.invoice,request.manufacturer,request.customerMFGItemNo,request.fromPODate,request.toPODate));
////    }
//
//    public List<Customers> getAllCustomers() {
//        return customerDetailsRepository.findCustomerList();
//    }
//
//    public List<ProductDetails> getAllProducts() {
//        return responseMapper.productDetailsMapper(productDetailsRepository.findAll());
//    }
//
//    public List<PODetails> getAllPOList() {
//        List<PODetails> list = new ArrayList<>();
//        poMasterRepository.findAll().forEach(po -> {
//
//            list.add(poDetailsMapper.poDetailsPOJOMapper(po, customerDetailsRepository.findCustomerNameOnly(po.getCustomerId())));
//        });
//        return list;
//    }
//
    public PODetails getAllOrderByCustomerIdAndPONumber(GetOrdersByCustomerAndPONumberRequest request,Requester requester) throws Exception {
var poEntity=poMasterRepository.findByPoNumberIgnoreCaseAndCustomerIdAndUserLevel(request.getPoNumber(),request.getCustomerId(),requester.getUserLevel());
      if(poEntity.isPresent())
      {
          return poDetailsMapper.poDetailsPOJOMapper(poEntity.get());
      }
      else
      {
          throw new Exception("Record not found !!");
      }

    }

    public List<PODetailAsList> getAllPurchaseOrder(ReportsFilterRequest request,Requester requester) throws Exception {
        List<PODetailAsList> result=new ArrayList<>();
        Specification<POMasterEntity> test = Specification.where(null);
        if (request.getCustomerId() != null && request.getCustomerId() != 0) {
            test = test.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerId"), request.getCustomerId()));
        }if(request.getFromDate()!=null && request.getToDate()!=null)
        {
            if(request.getFromDate().after(request.getToDate())) throw new Exception("From Date can not be greater that to date..");
            test=test.and((r,q,c)->c.between(r.get("poDate"),request.getFromDate(),request.getToDate()));
        }
        test = test.and((r, q, c) -> r.get("orderStatus").in(Constants.POStatus.getStatusList(request.getStatus() < 5 ? request.getStatus() : 1)));
        test=test.and((r,q,c)-> c.equal(r.get("userLevel"),requester.getUserLevel()));
        var x = poMasterRepository.findAll(test);
        if (request.getStatus() == 5) {
            x.forEach(po->
            {
                po.getProductOrderManagerEntity().forEach(order -> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment ->
                        shipment.getSupplierDeliveryDate() == null).collect(Collectors.toList())));
                //  entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
            });
        } else if (request.getStatus() == 6) {
            x.forEach(po->
                    { po.getProductOrderManagerEntity().forEach(order-> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment->
                            shipment.getSupplierDeliveryDate() != null&& (shipment.getInvoiceDate() == null ||"".equals(shipment.getInvoiceNo()))).collect(Collectors.toList())));}
                    //entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
            );
        }
x.forEach(p-> result.add(new PODetailAsList(p.getId(), p.getPoNumber(), p.getPoDate(), p.getOrderStatus(), p.getTotalAmount(),p.getCustomerId(),p.getCustomerDetailsEntity().getCustomerName(),p.getCreatedBy(),p.getCreatedDate(),p.getModifiedDate(),p.getPoDocumentName())));
return result;
    }

    public String updateStatus(Long poId,Requester request,String requestedStatus) throws Exception {
        String status;
        switch (requestedStatus)
        {
            case "cancel":
                status=Constants.POStatus.CANCEL_PO;
                break;
            case "amend":
                status=Constants.POStatus.AMENDED_PO;
                break;
            case "complete":
                status=Constants.POStatus.COMPLETED_PO;
                break;
            case "active":
                status=Constants.POStatus.ACTIVE_PO;
                break;
            default:
                throw new Exception("InvalidRequest");
        }
        if(poMasterRepository.existsByUserLevelAndId(request.getUserLevel(), poId))
        {
            var po=poMasterRepository.getById(poId);
            po.setOrderStatus(status);
            poMasterRepository.save(po);
            return "Status Updated";
        }else
        {
            throw new Exception("PO does not exist");
        }
    }

    public List<PODetails> getFilteredReport(RequestStructure<FilteredReportRequest> request) {
        var req=request.getRequest();
        List<POMasterEntity> poMasterEntities = new ArrayList<>();
        var statusList=req.getStatus()==null||req.getStatus().size()==0?Constants.POStatus.getStatusList(5):req.getStatus();
        if(req.getCustomer()!=null&&req.getMfgItem()!=null)
        {
            poMasterEntities= poMasterRepository.findByOrderStatusInAndUserLevelAndCustomerIdAndProductOrderManagerEntity_ProductId(statusList,request.getRequester().getUserLevel(),req.getCustomer(),req.getMfgItem());
        }else if(req.getCustomer()!=null){
            poMasterEntities=  poMasterRepository.findByOrderStatusInAndUserLevelAndCustomerId(statusList,request.getRequester().getUserLevel(),req.getCustomer());
        }else if(req.getMfgItem()!=null){
            poMasterEntities=   poMasterRepository.findByOrderStatusInAndUserLevelAndProductOrderManagerEntity_ProductId(statusList,request.getRequester().getUserLevel(),req.getMfgItem());
        }else if(req.getManufacturer()!=null){
         //   will add logic in some time
        }
        List<PODetails> responsePOList=new ArrayList<>();
        poMasterEntities.forEach(x->{
            responsePOList.add( poDetailsMapper.poDetailsPOJOMapper(x));
        });
        return responsePOList;
    }
}
