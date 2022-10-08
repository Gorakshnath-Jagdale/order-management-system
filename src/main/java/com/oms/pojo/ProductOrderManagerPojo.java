package com.oms.pojo;

import com.oms.models.ProductDetailsEntity;
import lombok.Data;

import java.util.Date;
import java.util.Set;

//CustomerDetailsPojo -> to THis
@Data
public class ProductOrderManagerPojo {

    private Date poDate;
    private Long customerId;
    private Long id;
    private Long productId;
    private ProductDetails mfgItemNumber;
    private Set<ProductShipmentManagerPojo> productShipmentDetails;
    private String customerItemNo;
    private String orderStatus;
    private String pendingQty;
    private String poNumber;
    private String poQuantity;
    private String remarks;
    private String suppliedQty;
    private float pov;//pending order value
    private float price;
    private float totalAmount;//pending order value
}
