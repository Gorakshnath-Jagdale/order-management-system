package com.oms.pojo;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PODetails {

    private String poDate;
    private String poNumber;
    private String customerName;
    private String orderStatus;
    private float totalAmount;

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

}