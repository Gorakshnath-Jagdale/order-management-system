package com.oms.service;

import com.oms.dto.RequestStructure;
import com.oms.dto.requests.ReportsFilterRequest;
import com.oms.dto.responses.ReportsFilterResponse;
import com.oms.mapper.response.ReportsMapper;
import com.oms.models.POMasterEntity;
import com.oms.models.ProductDetailsEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.models.ProductShipmentManagerEntity;
import com.oms.models.repository.POMasterRepository;
import com.oms.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportsService {
    private final POMasterRepository poMasterRepository;

    public List<ReportsFilterResponse> getFilteredOrderDetails(RequestStructure<ReportsFilterRequest> request) {
        var requestBody = request.getRequest();

        Specification<POMasterEntity> test = Specification.where(null);

        // INITIALLY ADDING FILTER FOR USER-LEVEL


        if (requestBody.getCustomerId() != null && requestBody.getCustomerId() != 0) {

            test = test.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerId"), requestBody.getCustomerId()));
        }
        if (requestBody.getManufacturer() != null && !requestBody.getManufacturer().equalsIgnoreCase("")) {


            if (requestBody.getProductId() != null && requestBody.getProductId() != 0) {


                test = test.and((root, query, criteriaBuilder) -> {
                    Join<POMasterEntity, ProductOrderManagerEntity> orderPORelation = root.join("productOrderManagerEntity");
                    Join<ProductOrderManagerEntity, ProductDetailsEntity> orderScheduleRelation = orderPORelation.join("productDetails");
                    return criteriaBuilder.equal(orderScheduleRelation.get("id"), requestBody.getProductId());
                });

            } else {
                test = test.and((root, query, criteriaBuilder) -> {
                    Join<POMasterEntity, ProductOrderManagerEntity> orderPORelation = root.join("productOrderManagerEntity");
                    Join<ProductOrderManagerEntity, ProductDetailsEntity> orderScheduleRelation = orderPORelation.join("productDetails");
                    return criteriaBuilder.equal(orderScheduleRelation.get("manufacturer"), requestBody.getManufacturer());
                });
            }
        }

        if (requestBody.getStatus() == 5) {
            test = test.and((root, query, criteriaBuilder) -> {
                Join<POMasterEntity, ProductOrderManagerEntity> orderPORelation = root.join("productOrderManagerEntity");
                Join<ProductOrderManagerEntity, ProductShipmentManagerEntity> orderScheduleRelation = orderPORelation.join("productShipmentDetails");
                return criteriaBuilder.isNull(orderScheduleRelation.get("supplierDeliveryDate"));
            });
        } else if (requestBody.getStatus() == 6) {
            test = test.and((root, query, criteriaBuilder) -> {
                Join<POMasterEntity, ProductOrderManagerEntity> orderPORelation = root.join("productOrderManagerEntity");
                Join<ProductOrderManagerEntity, ProductShipmentManagerEntity> orderScheduleRelation = orderPORelation.join("productShipmentDetails");
                return criteriaBuilder.isNotNull(orderScheduleRelation.get("supplierDeliveryDate"));
            });
            test = test.and((root, query, criteriaBuilder) -> {
                Join<POMasterEntity, ProductOrderManagerEntity> orderPORelation = root.join("productOrderManagerEntity");
                Join<ProductOrderManagerEntity, ProductShipmentManagerEntity> orderScheduleRelation = orderPORelation.join("productShipmentDetails");
                return criteriaBuilder.isNull(orderScheduleRelation.get("invoiceNo"));
            });
        }
            test = test.and((r, q, c) -> r.get("orderStatus").in(Constants.POStatus.getStatusList(requestBody.getStatus() < 5 ? requestBody.getStatus() : 1)));

        var x = poMasterRepository.findAll(test);
        List<ReportsFilterResponse> response = new ArrayList<>();

        x.forEach(po -> {
            po.getProductOrderManagerEntity().forEach(order -> {
                order.getProductShipmentDetails().forEach(schedule -> {
                    var temp = new ReportsFilterResponse();

                    temp.setPoDate(po.getPoDate());
                    temp.setPoNumber(po.getPoNumber());
                    temp.setOrderStatus(po.getOrderStatus());
                    temp.setCustomerId(po.getCustomerId());
                    temp.setPoId(po.getId());
                    temp.setCustomerName(po.getCustomerDetailsEntity().getCustomerName());
                    temp.setCustomerItemNo(order.getCustomerItemNo());
                    temp.setManufacturer(order.getProductDetails().getManufacturer());
                    temp.setMfgItemNumber(order.getProductDetails().getMfgItemNumber());
                    temp.setPrice(order.getPrice());
                    temp.setProductOrderId(order.getId());
                    temp.setScheduleQty(schedule.getScheduleQty());
                    temp.setPendingQty(schedule.getPendingQty());
                    temp.setSuppliedQty(schedule.getSuppliedQty());
                    temp.setPov(schedule.getPov());
                    temp.setEsplPO(schedule.getEsplPO());
                    temp.setInvoiceNo(schedule.getInvoiceNo());
                    temp.setInvoiceDate(schedule.getInvoiceDate());
                    temp.setCustomerRequestedDate(schedule.getCustomerRequestedDate());
                    temp.setSupplierDeliveryDate(schedule.getSupplierDeliveryDate());
                    temp.setRemarks(schedule.getRemarks());
                    temp.setProductScheduleId(schedule.getId());
                    response.add(temp);
                });
            });
        });

        return response;
    }
}
