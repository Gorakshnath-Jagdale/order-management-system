package com.oms.dto.responses;


import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PODetailAsList {
    private Long id;
    private String poDate;
    private String poNumber;
    private Long customerId;
    private String customerName;
    private String orderStatus;
    private double totalAmount;

    public PODetailAsList(){

    }
    public PODetailAsList(Long id,String poNumber,Date poDate,String orderStatus,double totalAmount,Long customerId,String customerName)
    {
        this.id=id;
        this.poDate=new SimpleDateFormat("dd/MM/yyyy").format(poDate);
        this.poNumber=poNumber;
        this.orderStatus=orderStatus;
        this.totalAmount=totalAmount;
        this.customerId=customerId;
        this.customerName=customerName;
    }
    private String getMyDate(Date date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (Exception e) {
            return date.toString();
        }
    }

}