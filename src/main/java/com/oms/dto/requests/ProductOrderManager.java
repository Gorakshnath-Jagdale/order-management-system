package com.oms.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/* Dummy of ProductOrderManagerEntity */
@Data
public class ProductOrderManager {
    private Long id;
    private Long productId;
    private Product productDetails;
    private String customerItemNo;
    private String pendingQty;
    private float pov;      //pending order value
    private float price;
    private String poId;
    private String poQuantity;
    private float totalAmount;
    private String remarks;
    private Date poDate;
    private List<ProductShipmentManager> productShipmentDetails;

}
