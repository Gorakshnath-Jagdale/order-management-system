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
    private String poDocumentName;
    private String orderStatus;
    private double totalAmount;
    private String createdBy;
    private String createdDate;
    private String modifiedDate;

    public PODetailAsList(){

    }
    public PODetailAsList(Long id,String poNumber,Date poDate,String orderStatus,double totalAmount,Long customerId,String customerName,Integer createdBy,Date createdDate,Date modifiedDate,String poDocumentName)
    {
        this.id=id;
        this.poDate=getMyDate(poDate);
        this.poNumber=poNumber;
        this.orderStatus=orderStatus;
        this.totalAmount=totalAmount;
        this.customerId=customerId;
        this.customerName=customerName;
        this.createdBy= String.valueOf(createdBy);
        this.createdDate=getMyDate(createdDate);
         this.modifiedDate=getMyDate(modifiedDate);
         this.poDocumentName=poDocumentName;
    }
    private String getMyDate(Date date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (Exception e) {
            return "";
        }
    }

}