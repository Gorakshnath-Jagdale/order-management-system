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
    private double price;
    private String poId;
    private long poQuantity;
    private double totalAmount;
    private List<ProductShipmentManager> productShipmentDetails;

}
