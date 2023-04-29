package com.oms.mapper.response;

import com.oms.dto.requests.Product;
import com.oms.models.ProductDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring",imports = Date.class)
public interface ProductMapper {

    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "createdDate", expression = "java(new Date())")
    ProductDetailsEntity productToProductDetailsEntity(Product product,Integer createdBy);
    @Mapping(target = "modifiedBy", source = "modifiedBy")
    @Mapping(target = "modifiedDate", expression = "java(new Date())")
    void updateProductToProductDetailsEntity(@MappingTarget ProductDetailsEntity entity, Product product,Integer modifiedBy);

    Product ProductDetailsEntityToProduct(ProductDetailsEntity entity);

    List<Product> productDetailsEntityListToProductList(List<ProductDetailsEntity> entities);

}
