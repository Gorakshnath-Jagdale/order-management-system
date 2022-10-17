package com.oms.pojo.requestPojo;

import lombok.Data;

import java.util.List;

@Data
public class GetExcelRequest {
    private String poNumber;
    private boolean isSingleCustomer;
    private boolean isGetCompleteOrders;//true if want complete orders else false to get current non completed orders
    private List<Long> customerList;// It will be used where isSingleCustomer is false
}