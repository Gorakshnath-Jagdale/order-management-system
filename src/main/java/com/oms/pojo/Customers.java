package com.oms.pojo;

import lombok.Data;

@Data
public class Customers {
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerContact;
    private String customerAddress;

    public Customers(Long customerId, String customerName, String customerEmail, String customerContact, String customerAddress) {
        this.customerId = String.valueOf(customerId);
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerContact = customerContact;
        this.customerAddress = customerAddress;
    }
}
