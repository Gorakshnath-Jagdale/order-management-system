package com.oms.dto.responses;

import lombok.Data;

import java.util.Date;

@Data
public class ReportsFilterResponse {
    private String customerName;
    private Date poDate;
    private String poNumber;
    private String orderStatus;
    private Long customerId;
    private Long poId;

    //order
    private String customerItemNo;
    private String manufacturer;
    private String mfgItemNumber;
    private double price;
    private Long productOrderId;


    private Long scheduleQty;
    private Long pendingQty;
    private Long suppliedQty;
    private double pov;
    //private Long productOrderId;
    private String esplPO;
    private String invoiceNo;
    private Date invoiceDate;
    private Date customerRequestedDate;
    private Date supplierDeliveryDate;
    private String remarks;
    private Long productScheduleId;
}
