package com.oms.dto.requests;

import lombok.Data;

import java.util.Date;

@Data
public class BulkSupplierDeliveryDateUpdateRequest {
    private long totalDeliveryQuantity;
    private Date supplierDeliveryDate;
    private long productOrderId; //ProductOrderManagerEntity -> ID
}
