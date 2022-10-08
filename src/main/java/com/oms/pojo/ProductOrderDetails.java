package com.oms.pojo;

import lombok.Data;

/*
Added this class to return only required fields of product orders from PO.
 */
@Data
public class ProductOrderDetails{
    private String mfgItemNumber;
    private String orderStatus;
    private float price;
    private String poQuantity;
    private String pendingQty;
    private String suppliedQty;
    private float pov;//pending order value
    private float totalAmount;//pending order value
}