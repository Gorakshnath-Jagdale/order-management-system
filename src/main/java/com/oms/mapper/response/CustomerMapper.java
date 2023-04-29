package com.oms.mapper.response;

import com.oms.dto.requests.Customer;
import com.oms.models.CustomerDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Date.class})
public interface CustomerMapper {
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "createdDate", expression = "java(new Date())")
    CustomerDetailsEntity customerToCustomerDetailsEntity(Customer customer, Long createdBy);

    @Mapping(target = "modifiedBy", source = "modifiedBy")
    @Mapping(target = "modifiedDate", expression = "java(new Date())")
    void updateCustomerToCustomerDetailsEntity(@MappingTarget CustomerDetailsEntity entity, Customer customer, Long modifiedBy);

    Customer customerDetailsEntityToCustomer(CustomerDetailsEntity entity);

    List<Customer> customerDetailsEntityListToCustomerList(List<CustomerDetailsEntity> entities);
}
