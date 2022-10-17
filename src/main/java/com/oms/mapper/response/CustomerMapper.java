package com.oms.mapper.response;

import com.oms.dto.requests.Customer;
import com.oms.models.CustomerDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDetailsEntity customerToCustomerDetailsEntity(Customer customer);
     void updateCustomerToCustomerDetailsEntity(@MappingTarget CustomerDetailsEntity entity, Customer customer);

    Customer customerDetailsEntityToCustomer(CustomerDetailsEntity entity);
    List<Customer> customerDetailsEntityListToCustomerList(List<CustomerDetailsEntity> entities);
}
