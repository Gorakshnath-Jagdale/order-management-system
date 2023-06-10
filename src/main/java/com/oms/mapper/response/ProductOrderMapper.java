package com.oms.mapper.response;

import com.oms.dto.requests.ProductOrderManager;
import com.oms.dto.requests.ProductShipmentManager;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.models.ProductShipmentManagerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", imports = Date.class)
public interface ProductOrderMapper {

    List<ProductOrderManagerEntity> productOrderListToProductOrderManagerEntityList(List<ProductOrderManager> productOrderManagerDto);

    @Mapping(target = "productId", source = "productOrderManagerDto.productDetails.id")
    @Mapping(target = "createdDate", expression = "java(new Date())")
    ProductOrderManagerEntity ProductOrderToProductOrderManagerEntity(ProductOrderManager productOrderManagerDto);

    ProductShipmentManagerEntity ProductShipmentToProductShipmentManagerEntity(ProductShipmentManager ProductShipmentManager);


    List<ProductOrderManager> productOrderEntityListToProductOrderManagerList(List<ProductOrderManagerEntity> productOrderManagerDto);

    ProductShipmentManager productShipmentManagerEntityToProductShipmentManager(ProductShipmentManagerEntity productOrderManagerEntity);
}
