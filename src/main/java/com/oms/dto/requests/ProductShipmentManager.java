package com.oms.dto.requests;

import lombok.Data;

import java.util.Date;

@Data
public class ProductShipmentManager {
    private Long id;
    private Long scheduleQty;
    private Long pendingQty;
    private Long suppliedQty;
    private float pov;
    private Long productOrderId;
    private String esplPO;
    private String invoiceNo;
    private Date invoiceDate;
    private Date customerRequestedDate;
    private Date supplierDeliveryDate;
    private String remarks;
    private String modifiedBy;
    private String createdBy;
    private Date createdDate;
    private Date modifiedDate;
}
