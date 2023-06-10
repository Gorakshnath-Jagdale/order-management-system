package com.oms.mapper;

import com.oms.models.CustomerDetailsEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.pojo.CustomerDetailsPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Date.class})
public interface NewCustomerMapper {

    //    @Mapping(target = "createdBy",defaultValue = "Me")
//     @Mapping(target = "createdDate",expression = "java(new Date())")
//     @Mapping(target = "modifiedDate",expression = "java(new Date())")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
//    @Mapping(target = "modifiedBy",defaultValue = "Me")
    //  @Mapping(target = "customerOrders" ,ignore = true)
    CustomerDetailsEntity customerDetailsEntityMapper(CustomerDetailsPojo customerDetails);

    default List<ProductOrderManagerEntity> OrderManagerEntityMapper(CustomerDetailsPojo customerDetails) {
        customerDetails.getCustomerOrders().forEach(x -> {
                    if (customerDetails.getId() != null) {
                        x.setCustomerId(customerDetails.getId());
                    }
                }
        );
        return customerDetails.getCustomerOrders();
    }
}
