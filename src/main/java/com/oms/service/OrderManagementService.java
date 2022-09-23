package com.oms.service;

import com.oms.models.CustomerDetailsEntity;
import com.oms.models.repository.CustomerDetailsRepository;
import com.oms.pojo.CustomerDetailsPojo;
import com.oms.pojo.UserDetailsPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final CustomerDetailsRepository customerDetailsRepository;

    @Transactional
    public CustomerDetailsPojo saveNewOrderDetails(CustomerDetailsPojo customerDetails) {
       var savedCustomerAndOrder= customerDetailsRepository.save(customerDetailsMapping(customerDetails));
return customerDetailsPojoMapping(savedCustomerAndOrder);
    }

    private CustomerDetailsEntity customerDetailsMapping(CustomerDetailsPojo customerDetails) {
        var customerDetailsEntity = new CustomerDetailsEntity();
        customerDetailsEntity.setId(customerDetails.getId());
        customerDetailsEntity.setCustomerAddress(customerDetails.getCustomerAddress());
        customerDetailsEntity.setCustomerEmail(customerDetails.getCustomerEmail());
        customerDetailsEntity.setCustomerName(customerDetails.getCustomerName());
        customerDetailsEntity.setCustomerOrders(customerDetails.getCustomerOrders());
        customerDetailsEntity.setCreatedBy(customerDetails.getCreatedBy());
        customerDetailsEntity.setModifiedBy(null);
        return customerDetailsEntity;
    }
    private CustomerDetailsPojo customerDetailsPojoMapping(CustomerDetailsEntity customerDetails) {
        var customerDetailsPojo = new CustomerDetailsPojo();
        customerDetailsPojo.setId(customerDetails.getId());
        customerDetailsPojo.setCustomerAddress(customerDetails.getCustomerAddress());
        customerDetailsPojo.setCustomerEmail(customerDetails.getCustomerEmail());
        customerDetailsPojo.setCustomerName(customerDetails.getCustomerName());
        customerDetailsPojo.setCustomerOrders(customerDetails.getCustomerOrders());
        customerDetailsPojo.setCreatedBy(customerDetails.getCreatedBy());
        customerDetailsPojo.setModifiedBy(customerDetailsPojo.getModifiedBy());
        return customerDetailsPojo;
    }
}
