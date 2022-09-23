package com.oms.service;

import com.oms.mapper.ResponseMapper;
import com.oms.models.repository.CustomerDetailsRepository;
import com.oms.pojo.CustomerDetailsPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final CustomerDetailsRepository customerDetailsRepository;
    private final ResponseMapper responseMapper;

    @Transactional
    public CustomerDetailsPojo saveNewOrderDetails(CustomerDetailsPojo customerDetails) {
       return responseMapper.customerDetailsPojoMapper(customerDetailsRepository.save(responseMapper.customerDetailsEntityMapper(customerDetails)));
    }
}
