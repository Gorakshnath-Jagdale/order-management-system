package com.oms.dto.requests;

import lombok.Data;

import java.util.Date;

@Data
public class BulkInvoiceDetailsUpdateRequest {
    private long totalDeliveryQuantity;
    private String invoiceNo;
    private String esplpo;
    private Date invoiceDate;
    private long productOrderId; //ProductOrderManagerEntity -> ID
}
