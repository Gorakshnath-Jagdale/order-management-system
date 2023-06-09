package com.oms.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/* Dummy of POMasterEntity */

/* To fetch details with PO Number we will use this response class*/
@Data
public class PODetails {

    private Long id;
    private Date poDate;
    private String poNumber;
    private double totalAmount;
    private String orderStatus;
    private String poDocumentName;
   // private int userLevel;
    private Customer customerDetailsEntity;
    private List<ProductOrderManager> productOrderManagerEntity;


}
