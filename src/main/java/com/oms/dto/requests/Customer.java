package com.oms.dto.requests;


import lombok.Data;

/* Dummy of CustomerDetailsEntity */
/* Same pojo will be used for update customer */
@Data
public class Customer {
    private Long id;
    private int code;//no purpose for now
    private String customerAddress;
    private String customerContact;
    private String customerEmail;
    private String customerName;
    private String gstin;
    private String stateName;
    private int paymentTerm;
}
