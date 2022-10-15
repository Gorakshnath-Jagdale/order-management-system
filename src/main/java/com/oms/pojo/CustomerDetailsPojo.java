package com.oms.pojo;

import com.oms.models.ProductOrderManagerEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CustomerDetailsPojo {

    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String customerContact;
    private Date poDate;
    private String poStatus;
    private String poNumber;
    private float totalAmount;
    private List<ProductOrderManagerEntity> customerOrders;
}


