//package com.oms.pojo;
//
//import com.oms.models.CustomerDetailsEntity;
//import lombok.Data;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//@Data
//public class OrderDetailsResponse {
//    private Long id;
//    private String poNumber;
//    private String poDate;
//    private String customerPartNo;
//    private String mfgItemNo;
//    private String itemDetails;
//    private String maker;
//    private float price;
//    private String poQuantity;
//    private String customerRequestedDate;
//    private String suppliedQty;
//    private String pendingQty;
//    private String ESPL_PO_OR_EBIS_NO;
//    private String supplierDeliveryDate;
//    private String invoiceNo;
//    private String endCustomerBillDate;
//    private String pov;//pending order value
//    private String totalAmount;//pending order value
//    private String remarks;
//    private String createdBy;
//    private Date createdDate;
//    private String modifiedBy;
//    private Date modifiedDate;
//    private String customerId;
//    private String customerName;
//
//
//
//
//
//    public void setPoDate(Date poDate){
//        this.poDate =getMyDate( poDate);
//    }
//
//    public void setCustomerRequestedDate(Date customerRequestedDate) {
//        this.customerRequestedDate =getMyDate(customerRequestedDate);
//    }
//
//    public void setSupplierDeliveryDate(Date supplierDeliveryDate) {
//        this.supplierDeliveryDate = getMyDate(supplierDeliveryDate);
//    }
//
//    public void setEndCustomerBillDate(Date endCustomerBillDate) {
//        this.endCustomerBillDate = getMyDate(endCustomerBillDate);
//    }
//   private String getMyDate(Date date) {try {
//       return  new SimpleDateFormat("dd/MM/yyyy").format(date);
//   }catch(Exception e)
//   {
//       return date.toString();
//   }
//
//    }
//}
