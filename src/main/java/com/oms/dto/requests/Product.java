package com.oms.dto.requests;

import lombok.Data;

/* Dummy of ProductDetailsEntity */
@Data
public class Product {
    private Long id;
    private String manufacturer;
    private String mfgItemNumber;
    private String productDetails;
}
