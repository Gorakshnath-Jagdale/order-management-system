package com.oms.service;

import com.oms.mapper.ResponseMapper;
import com.oms.models.CustomerDetailsEntity;
import com.oms.models.OrderManagerEntity;
import com.oms.models.repository.CustomerDetailsRepository;
import com.oms.pojo.CustomerDetailsPojo;
import com.oms.service.util.ExcelGeneratorService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final CustomerDetailsRepository customerDetailsRepository;
    private final ResponseMapper responseMapper;
    private final ExcelGeneratorService excelGeneratorService;

    @Transactional
    public CustomerDetailsPojo saveNewOrderDetails(CustomerDetailsPojo customerDetails) throws Exception {
        if (customerDetails.getId() != null) {
            throw new Exception("To save id should be empty.");
        } else {
            return responseMapper.customerDetailsPojoMapper(customerDetailsRepository.save(responseMapper.customerDetailsEntityMapper(customerDetails)));
        }
    }

    @Transactional
    public CustomerDetailsPojo updateOrderDetails(CustomerDetailsPojo customerDetails) throws Exception {
        if (customerDetails.getId() == null) {
            throw new Exception("ID should not be empty.");
        } else {
            return responseMapper.customerDetailsPojoMapper(customerDetailsRepository.save(responseMapper.customerDetailsEntityMapper(customerDetails)));
        }

    }


    public InputStream getAllOrdersByCustomerName(CustomerDetailsPojo customerDetails) throws IOException {
       var customerOrderDetails= customerDetailsRepository.findByCustomerNameIsIgnoreCaseOrCustomerEmailIsIgnoreCaseOrCustomerAddressIsIgnoreCase(customerDetails.getCustomerName(),customerDetails.getCustomerEmail(),customerDetails.getCustomerAddress());

       List<OrderManagerEntity> orderList=new ArrayList<>();
        customerOrderDetails.forEach(x-> orderList.addAll(x.getCustomerOrders()));


        return excelGeneratorService.getOrderDetailsExcel(orderList,customerOrderDetails.size()==1);
    }
}
