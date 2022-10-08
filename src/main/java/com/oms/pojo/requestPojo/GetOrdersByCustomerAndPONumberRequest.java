package com.oms.pojo.requestPojo;

import lombok.Data;

@Data
public class GetOrdersByCustomerAndPONumberRequest {
    private Long customerId;
    private String poNumber;
}
