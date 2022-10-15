package com.oms.pojo;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

//CustomerDetailsPojo -> ProductOrderManagerPojo -> to THis
@Data
public class ProductShipmentManagerPojo {
    private String customerRequestedDate;
    private String supplierDeliveryDate;
    private String pendingQty;
    private String esplPO;
    private String invoiceNo;
    private Long id;
private Long productOrderId;
    private String getMyDate(Date date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public void setCustomerRequestedDate(Date customerRequestedDate) {
        this.customerRequestedDate = getMyDate(customerRequestedDate);
    }

    public void setSupplierDeliveryDate(Date supplierDeliveryDate) {
        this.supplierDeliveryDate = getMyDate(supplierDeliveryDate);
    }

}
