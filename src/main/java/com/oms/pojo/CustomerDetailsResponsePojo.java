package com.oms.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomerDetailsResponsePojo {

    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String customerContact;
    private String poDate;
    private String poStatus;
    private String poNumber;
    private float totalAmount;
    private List<ProductOrderManagerPojo> customerOrders;

    public CustomerDetailsResponsePojo(Long customerId, String customerName,String customerEmail, String customerContact, String customerAddress) {
        this.id= customerId;
        this.customerName=customerName;
        this.customerEmail=customerEmail;
        this.customerContact=customerContact;
        this.customerAddress=customerAddress;
    }
    private String getMyDate(Date date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (Exception e) {
            return date.toString();
        }
    }


    public void setPoDate(Date poDate) {
        this.poDate = getMyDate(poDate);
    }
    public void setPoDateString(String poDate) {
        this.poDate = poDate;
    }

}


