package com.oms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(schema = "OMS", name = "PRODUCT_ORDER_MANAGER")
public class ProductOrderManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ORDER_ID", nullable = false)
    private Long id;
    @Column(name = "PO_NUMBER", nullable = false)
    private String poNumber;
    @Column(name = "PO_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date poDate;
    @Column(name = "CUSTOMER_ITEM_NO", nullable = false)
    private String customerItemNo;

    @Column(name = "PRICE", nullable = false)
    private float price;
    @Column(name = "PO_QTY", nullable = false)
    private String poQuantity;
    @Column(name = "PENDING_QTY", nullable = false)
    private String pendingQty;
    @Column(name = "SUPPLIED_QTY", nullable = false)
    private String suppliedQty;

    @Column(name = "POV", nullable = false)
    private float pov;//pending order value
    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private float totalAmount;//pending order value

    @Column(name = "REMARKS", nullable = false)
    private String remarks;

    @Column(name = "ORD_STATUS", nullable = false)
    private String orderStatus;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID",insertable = false, updatable = false)
    @JsonBackReference
    private CustomerDetailsEntity customerDetails;

    @ManyToOne  
  @JoinColumn(name = "PRODUCT_ID",insertable = false, updatable = false)
    @JsonBackReference(value = "test1")
    private ProductDetailsEntity mfgItemNumber;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "PRODUCT_ORDER_ID")
    @JsonBackReference(value = "test2")
    private List<ProductShipmentManagerEntity> productShipmentDetails;



    @PreUpdate
    public void setModifiedDateCurrent() {
        this.modifiedDate = new Date();
    }
    @PrePersist
    public void setCreatedDateCurrent() {
        this.createdDate =  new Date();
    }
}