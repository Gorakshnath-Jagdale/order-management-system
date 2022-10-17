package com.oms.mapper.response;

import com.oms.dto.requests.ProductOrderManager;
import com.oms.dto.requests.ProductShipmentManager;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.models.ProductShipmentManagerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductOrderMapper {

    List<ProductOrderManagerEntity> productOrderListToProductOrderManagerEntityList(List<ProductOrderManager> productOrderManagerDto);
    ProductOrderManagerEntity ProductOrderToProductOrderManagerEntity(ProductOrderManager productOrderManagerDto);

    ProductShipmentManagerEntity ProductShipmentToProductShipmentManagerEntity(ProductShipmentManager ProductShipmentManager);


    List<ProductOrderManager> productOrderEntityListToProductOrderManagerList(List<ProductOrderManagerEntity> productOrderManagerDto);
}
