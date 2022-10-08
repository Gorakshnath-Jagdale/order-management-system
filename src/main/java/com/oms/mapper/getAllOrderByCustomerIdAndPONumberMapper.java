package com.oms.mapper;

import com.oms.models.CustomerDetailsEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.pojo.CustomerDetailsResponsePojo;
import com.oms.pojo.ProductOrderManagerPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface getAllOrderByCustomerIdAndPONumberMapper {

    CustomerDetailsResponsePojo responseMapper(CustomerDetailsEntity customerDetails);


    ProductOrderManagerPojo productOrderManagerEntityToProductOrderManagerPojo(ProductOrderManagerEntity productOrderManagerEntity);
}
