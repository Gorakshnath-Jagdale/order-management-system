package com.oms.pojo;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PODetails {

    private String poDate;
    private String poNumber;
    private Long customerId;
    private String customerName;
    private String orderStatus;
    private float totalAmount;

    public PODetails(){

    }
    public PODetails(String poNumber,Date poDate,String orderStatus,float totalAmount)
    {
        this.poDate=new SimpleDateFormat("dd/MM/yyyy").format(poDate);
        this.poNumber=poNumber;
        this.orderStatus=orderStatus;
        this.totalAmount=totalAmount;
    }
    private String getMyDate(Date date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (Exception e) {
            return date.toString();
        }
    }

}