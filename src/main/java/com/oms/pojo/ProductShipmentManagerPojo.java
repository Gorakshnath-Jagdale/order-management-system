package com.oms.pojo;

import lombok.Data;

import java.util.Date;

//CustomerDetailsPojo -> ProductOrderManagerPojo -> to THis
@Data
public class ProductShipmentManagerPojo {
    private Date customerRequestedDate;
    private Date supplierDeliveryDate;
    private Long pendingQty;
    private String esplPO;
    private String invoiceNo;
    private Long id;
}
