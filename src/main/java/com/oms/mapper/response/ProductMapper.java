package com.oms.mapper.response;

import com.oms.dto.requests.Product;
import com.oms.models.ProductDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDetailsEntity productToProductDetailsEntity(Product product);
    void updateProductToProductDetailsEntity(@MappingTarget ProductDetailsEntity entity, Product product);

    Product ProductDetailsEntityToProduct(ProductDetailsEntity entity);
    List<Product> productDetailsEntityListToProductList(List<ProductDetailsEntity> entities);

}
