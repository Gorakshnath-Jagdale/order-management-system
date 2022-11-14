package com.oms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(schema = "OMS_ADVANCE", name = "PRODUCT_SHIPMENT_MANAGER")
public class ProductShipmentManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_SHIPMENT_ID", nullable = false)
    private Long id;

    @Column(name = "SCHEDULE_QTY", nullable = false)
    private Long scheduleQty;


    @Column(name = "PENDING_QTY", nullable = false)
    private Long pendingQty;
    @Column(name = "SUPPLIED_QTY", nullable = false)
    private Long suppliedQty;

    @Column(name = "POV", nullable = false)
    private float pov;//pending order value

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "ESPL_PO_OR_EBIS_NO")
    private String esplPO;

    @Column(name = "SUPPLIER_DELIVERY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date supplierDeliveryDate;

    @Column(name = "CUSTOMER_REQ_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date customerRequestedDate;

    @Column(name = "INVOICE_NO")
    private String invoiceNo;


    @Column(name = "INVOICE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @Column(name = "CREATED_BY")
    private String createdBy;


    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

//    @Column(name = "CUSTOMER_ID")
//    private Long customerId;

    //check requirement
    @Column(name = "PRODUCT_ORDER_ID")
    private Long productOrderId;
//    @Column(name = "PRODUCT_ID", nullable = false)
//    private Long productId;
//    @Column(name = "PRODUCT_ID", nullable = false)
//    private Long productId;

//    @Column(name = "PRODUCT_ID")
//    private Long productId;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ORDER_ID",insertable = false, updatable = false)
    @JsonBackReference(value = "test")
    private ProductOrderManagerEntity productOrderManagerEntity;

//    @ManyToOne
//    @JoinColumn(name = "CUSTOMER_ID",insertable = false, updatable = false)
//    @JsonBackReference
//    private CustomerDetailsEntity customerDetails;
//


    @PreUpdate
    public void setModifiedDateCurrent() {
        this.modifiedDate = new Date();
    }
    @PrePersist
    public void setCreatedDateCurrent() {
        this.createdDate =  new Date();
    }

}
