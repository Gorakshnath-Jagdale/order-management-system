package com.oms.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CustomerDetailsResponsePojo {

    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String customerContact;
    private Date poDate;
    private String poStatus;
    private String poNumber;
    private float TotalAmount;
    private List<ProductOrderManagerPojo> customerOrders;
}


