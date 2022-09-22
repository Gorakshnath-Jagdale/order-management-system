package com.oms.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(schema = "OMS", name = "ORDER_MANAGER")
public class OrderManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID", nullable = false)
    private Long id;
    @Column(name = "PO_NUMBER", nullable = false)
    private String PONumber;
    @Column(name = "ORDER_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date PODate;
    @Column(name = "CUSTOMER_ID", nullable = false)
    private String customerId;
    @Column(name = "CUSTOMER_PART_NO", nullable = false)
    private String CustomerPartNo;
    @Column(name = "MFG_ITEM_NO", nullable = false)
    private String MFGItemNo;
    @Column(name = "ITEM_DETAILS", nullable = false)
    private String itemDetails;
    @Column(name = "MAKER", nullable = false)
    private String maker;
    @Column(name = "PRICE", nullable = false)
    private float price;
    @Column(name = "PO_QTY", nullable = false)
    private String POQuantity;
    @Column(name = "CUSTOMER_REQ_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date customerRequestedDate;
    @Column(name = "SUPPLIED_QTY", nullable = false)
    private String suppliedQty;
    @Column(name = "PENDING_QTY", nullable = false)
    private String pendingQty;
    @Column(name = "ESPL_PO_OR_EBIS_NO", nullable = false)
    private String ESPL_PO_OR_EBIS_NO;
    @Column(name = "SUPPLIER_DELIVERY_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date supplierDeliveryDate;
    @Column(name = "INVOICE_NO", nullable = false)
    private String invoiceNo;
    @Column(name = "END_CUST_BILL_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endCustomerBillDate;
    @Column(name = "POV", nullable = false)
    private String pob;//pending order value
    @Column(name = "REMARKS", nullable = false)
    private String remarks;
    @Column(name = "PO_IMAGE", nullable = false)
    private String poImageLink;
    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;
    @Column(name = "CREATED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "MODIFIED_BY", nullable = false)
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
}
