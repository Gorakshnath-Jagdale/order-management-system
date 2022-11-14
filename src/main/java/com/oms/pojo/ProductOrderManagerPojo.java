package com.oms.pojo;

import com.oms.models.ProductDetailsEntity;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

//CustomerDetailsPojo -> to THis
@Data
public class ProductOrderManagerPojo {

    private String poDate;
    private Long customerId;
    private Long id;
    private Long productId;
    private ProductDetails mfgItemNumber;
    private Set<ProductShipmentManagerPojo> productShipmentDetails;
    private String customerItemNo;
    private String orderStatus;
    private long pendingQty;
    private String poNumber;
    private long poQuantity;
    private String remarks;
    private String suppliedQty;
    private float pov;//pending order value
    private float price;
    private float totalAmount;//pending order value

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
