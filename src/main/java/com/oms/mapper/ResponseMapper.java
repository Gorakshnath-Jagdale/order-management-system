package com.oms.mapper;

import com.oms.models.CustomerDetailsEntity;
import com.oms.models.ProductDetailsEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.pojo.CustomerDetailsPojo;
import com.oms.pojo.OrderDetailsResponse;
import com.oms.pojo.ProductDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Date.class})
public interface ResponseMapper {

     CustomerDetailsPojo customerDetailsPojoMapper(CustomerDetailsEntity customerDetails);

     @Mapping(target = "createdBy",defaultValue = "Me")
//     @Mapping(target = "createdDate",expression = "java(new Date())")
//     @Mapping(target = "modifiedDate",expression = "java(new Date())")
          @Mapping(target = "createdDate",ignore = true)
     @Mapping(target = "modifiedDate",ignore = true)
     @Mapping(target = "modifiedBy",defaultValue = "Me")
     @Mapping(target = "customerOrders" ,expression = "java(OrderManagerEntityMapper(customerDetails.getCustomerOrders()))")
    CustomerDetailsEntity customerDetailsEntityMapper(CustomerDetailsPojo customerDetails);

   default List<ProductOrderManagerEntity> OrderManagerEntityMapper(List<ProductOrderManagerEntity> orderManagerEntities){
       orderManagerEntities.forEach(x->{
                    x.setCreatedBy("me");
                    x.setCreatedDate(new Date());
                    x.setModifiedDate(new Date());
                    x.setModifiedBy("Me");
                }
                );
        return orderManagerEntities;
    }

    List<OrderDetailsResponse> orderListMapper(List<ProductOrderManagerEntity> orderManagerEntities);

    @Mapping(target = "customerId",source = "customerDetails.id")
    @Mapping(target = "customerName",source = "customerDetails.customerName")
    OrderDetailsResponse  orderMapper(ProductOrderManagerEntity orderManagerEntity);

    List<ProductDetails> productDetailsMapper(List<ProductDetailsEntity> productDetailsList);
}
