package com.oms.service;

import com.oms.dto.RequestStructure;
import com.oms.dto.requests.ReportsFilterRequest;
import com.oms.dto.responses.ReportsFilterResponse;
import com.oms.models.POMasterEntity;
import com.oms.models.repository.POMasterRepository;
import com.oms.service.util.Constants;
import com.oms.service.util.ExcelGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsService {
    private final POMasterRepository poMasterRepository;
    private final ExcelGeneratorService excelGeneratorService;
    private final UserManagementService userManagementService;

    public List<ReportsFilterResponse> getFilteredOrderDetails(RequestStructure<ReportsFilterRequest> request) throws Exception {
        var requestBody = request.getRequest();
        Specification<POMasterEntity> test = Specification.where(null);
        if (requestBody.getPoNumber() != null && !Objects.equals(requestBody.getPoNumber(), "")) {
            test = test.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("poNumber"), "%"+requestBody.getPoNumber().trim()+"%"));
        }
        if (requestBody.getCustomerId() != null && requestBody.getCustomerId() != 0) {
            test = test.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerId"), requestBody.getCustomerId()));
        }
//        if (requestBody.getFromDate() != null && requestBody.getToDate() != null) {
//            if (requestBody.getFromDate().after(requestBody.getToDate()))
//                throw new Exception("From Date can not be greater that to date..");
//            test = test.and((r, q, c) -> c.between(r.get("poDate"), requestBody.getFromDate(), requestBody.getToDate()));
//        }
        test = test.and((r, q, c) -> r.get("orderStatus").in(Constants.POStatus.getStatusList(requestBody.getStatus())));
        var userAccessList = userManagementService.getTeamMemberList(request.getRequester().getUserId());
        test = test.and((r, q, c) -> r.get("createdBy").in(userAccessList));
        var x = poMasterRepository.findAll(test);

//Dead code - start
//        if (requestBody.getStatus() == 5) {
//            x.forEach(po ->
//            {
//                po.getProductOrderManagerEntity().forEach(order -> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment ->
//                        shipment.getSupplierDeliveryDate() == null).collect(Collectors.toList())));
//                //  entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
//            });
//        } else if (requestBody.getStatus() == 6) {
//            x.forEach(po ->
//                    {
//                        po.getProductOrderManagerEntity().forEach(order -> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment ->
//                                shipment.getSupplierDeliveryDate() != null && (shipment.getInvoiceDate() == null || "".equals(shipment.getInvoiceNo()))).collect(Collectors.toList())));
//                    }
//                    //  entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
//            );
//        }
//Dead code - END
        // INITIALLY ADDING FILTER FOR USER-LEVEL

        if (requestBody.getManufacturer() != null && !requestBody.getManufacturer().equalsIgnoreCase("")) {
            if (requestBody.getProductId() != null && requestBody.getProductId() != 0) {
                x.forEach(po -> po.setProductOrderManagerEntity(po.getProductOrderManagerEntity().stream().filter(order -> Objects.equals(order.getProductId(), requestBody.getProductId())).collect(Collectors.toList())));
            } else {
                x.forEach(po -> po.setProductOrderManagerEntity(po.getProductOrderManagerEntity().stream().filter(order -> Objects.equals(order.getProductDetails().getManufacturer(), requestBody.getManufacturer())).collect(Collectors.toList())));
            }
        }
        List<ReportsFilterResponse> response = new ArrayList<>();

        x.forEach(po -> {
            po.getProductOrderManagerEntity().forEach(order -> order.getProductShipmentDetails().forEach(schedule -> {
                var temp = new ReportsFilterResponse();
                temp.setPoDate(po.getPoDate());//2
                temp.setPoNumber(po.getPoNumber());//1
                temp.setOrderStatus(po.getOrderStatus());
                temp.setCustomerId(po.getCustomerId());
                temp.setPoId(po.getId());
                temp.setCustomerName(po.getCustomerDetailsEntity().getCustomerName());//3
                temp.setCustomerItemNo(order.getCustomerItemNo());//4
                temp.setManufacturer(order.getProductDetails().getManufacturer());//6
                temp.setProductDetails(order.getProductDetails().getProductDetails());
                temp.setMfgItemNumber(order.getProductDetails().getMfgItemNumber());//5
                temp.setPrice(order.getPrice());//7
                temp.setProductOrderId(order.getId());
                temp.setScheduleQty(schedule.getScheduleQty());//8
                temp.setPendingQty(schedule.getPendingQty());//11
                temp.setSuppliedQty(schedule.getSuppliedQty());//10
                temp.setPov(order.getPrice() * schedule.getPendingQty());//16
                temp.setEsplPO(schedule.getEsplPO());//12
                temp.setInvoiceNo(schedule.getInvoiceNo());//14
                temp.setInvoiceDate(schedule.getInvoiceDate());//15
                temp.setCustomerRequestedDate(schedule.getCustomerRequestedDate());//9
                temp.setSupplierDeliveryDate(schedule.getSupplierDeliveryDate());//13
                temp.setRemarks(schedule.getRemarks());//17
                temp.setProductScheduleId(schedule.getId());
                response.add(temp);
            }));
        });
        return response;
    }

    //Pending completed and all
    //Only active
    public List<ReportsFilterResponse> getFilteredOrderConsolidatedDetails(RequestStructure<ReportsFilterRequest> request) throws Exception {
        var requestBody = request.getRequest();
        Specification<POMasterEntity> test = Specification.where(null);
        if (requestBody.getPoNumber() != null && !Objects.equals(requestBody.getPoNumber(), "")) {
            test = test.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), requestBody.getPoNumber().trim()));
        }
        if (requestBody.getCustomerId() != null && requestBody.getCustomerId() != 0) {
            test = test.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerId"), requestBody.getCustomerId()));
        }
        if (requestBody.getFromDate() != null && requestBody.getToDate() != null) {
            if (requestBody.getFromDate().after(requestBody.getToDate()))
                throw new Exception("From Date can not be greater that to date..");
            test = test.and((r, q, c) -> c.between(r.get("poDate"), requestBody.getFromDate(), requestBody.getToDate()));
        }
        test = test.and((r, q, c) -> r.get("orderStatus").in(Constants.POStatus.getStatusList(requestBody.getStatus() < 5 ? requestBody.getStatus() : 1)));
        var userAccessList = userManagementService.getTeamMemberList(request.getRequester().getUserId());
        test = test.and((r, q, c) -> r.get("createdBy").in(userAccessList));
        var x = poMasterRepository.findAll(test);


        if (requestBody.getStatus() == 5) {
            x.forEach(po ->
            {
                po.getProductOrderManagerEntity().forEach(order -> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment ->
                        shipment.getSupplierDeliveryDate() == null).collect(Collectors.toList())));
                //  entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
            });
        } else if (requestBody.getStatus() == 6) {
            x.forEach(po ->
                    {
                        po.getProductOrderManagerEntity().forEach(order -> order.setProductShipmentDetails(order.getProductShipmentDetails().stream().filter(shipment ->
                                shipment.getSupplierDeliveryDate() != null && (shipment.getInvoiceDate() == null || "".equals(shipment.getInvoiceNo()))).collect(Collectors.toList())));
                    }
                    //  entities2.add( poDetailsMapper.poDetailsPOJOMapper(po));}
            );
        }
        // INITIALLY ADDING FILTER FOR USER-LEVEL

        if (requestBody.getManufacturer() != null && !requestBody.getManufacturer().equalsIgnoreCase("")) {
            if (requestBody.getProductId() != null && requestBody.getProductId() != 0) {
                x.forEach(po -> po.setProductOrderManagerEntity(po.getProductOrderManagerEntity().stream().filter(order -> Objects.equals(order.getProductId(), requestBody.getProductId())).collect(Collectors.toList())));
            } else {
                x.forEach(po -> po.setProductOrderManagerEntity(po.getProductOrderManagerEntity().stream().filter(order -> Objects.equals(order.getProductDetails().getManufacturer(), requestBody.getManufacturer())).collect(Collectors.toList())));
            }
        }
        List<ReportsFilterResponse> response = new ArrayList<>();

        x.forEach(po -> po.getProductOrderManagerEntity().forEach(order -> {
            var temp = new ReportsFilterResponse();
            temp.setPoDate(po.getPoDate());//2
            temp.setPoNumber(po.getPoNumber());//1
            temp.setOrderStatus(po.getOrderStatus());
            temp.setCustomerId(po.getCustomerId());
            temp.setPoId(po.getId());
            temp.setCustomerName(po.getCustomerDetailsEntity().getCustomerName());//3
            temp.setCustomerItemNo(order.getCustomerItemNo());//4
            temp.setManufacturer(order.getProductDetails().getManufacturer());//6
            temp.setProductDetails(order.getProductDetails().getProductDetails());
            temp.setMfgItemNumber(order.getProductDetails().getMfgItemNumber());//5
            temp.setPrice(order.getPrice());//7
            temp.setProductOrderId(order.getId());
            order.getProductShipmentDetails().forEach(schedule -> {
                temp.setScheduleQty((temp.getScheduleQty() == null ? 0 : temp.getScheduleQty()) + schedule.getScheduleQty());//8
                temp.setPendingQty((temp.getPendingQty() == null ? 0 : temp.getPendingQty()) + schedule.getPendingQty());//11
                temp.setSuppliedQty((temp.getSuppliedQty() == null ? 0 : temp.getSuppliedQty()) + schedule.getSuppliedQty());//10
                temp.setPov(temp.getPov() + (order.getPrice() * schedule.getPendingQty()));//16
            });

            response.add(temp);
        }));
        return response;
    }


    //EXCEL REPORT LOGIC
    public ByteArrayResource getExcelReport(RequestStructure<ReportsFilterRequest> request) throws Exception {
        var requestBody = request.getRequest();
        var test = requestBody.isConsolidate() ? getFilteredOrderConsolidatedDetails(request) : getFilteredOrderDetails(request);
        var isSingleCustomer = requestBody.getCustomerId() != null && requestBody.getCustomerId() != 0;
        return excelGeneratorService.getOrderDetailsExcel(test, isSingleCustomer, requestBody.isConsolidate());
    }
}
