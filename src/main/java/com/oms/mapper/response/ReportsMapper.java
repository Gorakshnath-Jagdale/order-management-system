package com.oms.mapper.response;

import com.oms.dto.responses.ReportsFilterResponse;
import com.oms.models.POMasterEntity;
import com.oms.models.ProductOrderManagerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportsMapper {
//    @Mapping(target = "customerName",source = "poMasterEntity.customerDetailsEntity.customerName")
//    @Mapping(target = "customerItemNo",source = "poMasterEntity.productOrderManagerEntity.customerItemNo")
//    @Mapping(target = "manufacturer",source = "poMasterEntity.productOrderManagerEntity.productDetails.manufacturer")
//    @Mapping(target = "mfgItemNumber",source = "poMasterEntity.productOrderManagerEntity.productDetails.mfgItemNumber")
//    @Mapping(target = "price",source = "poMasterEntity.productOrderManagerEntity.price")
//    @Mapping(target = "productOrderId",source = "poMasterEntity.productOrderManagerEntity.id")
//    @Mapping(target = "scheduleQty",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.scheduleQty")
//    @Mapping(target = "pendingQty",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.pendingQty")
//    @Mapping(target = "suppliedQty",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.suppliedQty")
//    @Mapping(target = "pov",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.pov")
//    @Mapping(target = "esplPO",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.esplPO")
//    @Mapping(target = "invoiceNo",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.invoiceNo")
//    @Mapping(target = "invoiceDate",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.invoiceDate")
//    @Mapping(target = "customerRequestedDate",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.customerRequestedDate")
//    @Mapping(target = "supplierDeliveryDate",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.supplierDeliveryDate")
//    @Mapping(target = "remarks",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.remarks")
//    @Mapping(target = "productScheduleId",source = "poMasterEntity.productOrderManagerEntity.productShipmentDetails.id")
//    ReportsFilterResponse PODetailsEntityToReportsFilterResponse(POMasterEntity poMasterEntity);
//
//    List<ReportsFilterResponse> PODetailsEntityToReportsFilterResponseListMapper(List<POMasterEntity> poMasterEntityList);
//
//
//    List<ProductOrderManagerEntity> productOrderManagerEntity
}
