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
@Table(schema = "OMS_ADVANCE", name = "PRODUCT_ORDER_MANAGER")
public class ProductOrderManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ORDER_ID", nullable = false)
    private Long id;
    @Column(name = "PO_ID", nullable = false)
    private Long poId;

    @Column(name = "CUSTOMER_ITEM_NO", nullable = false)
    private String customerItemNo;

    @Column(name = "PRICE", nullable = false)
    private double price;

    @Column(name = "PO_QTY", nullable = false)
    private Long poQuantity;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "PRODUCT_ID")
    private Long productId;


    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private double totalAmount;//pending order value


//    @Column(name = "ORD_STATUS", nullable = false)
//    private String orderStatus;

    @Column(name = "CREATED_BY")
    private String createdBy;


    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;


    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

//    @ManyToOne
//    @JoinColumn(name = "CUSTOMER_ID",insertable = false, updatable = false)
//    @JsonBackReference
//    private CustomerDetailsEntity customerDetails;

    @ManyToOne
  @JoinColumn(name = "PRODUCT_ID",insertable = false, updatable = false)
    @JsonBackReference(value = "test1")
    private ProductDetailsEntity productDetails;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "PRODUCT_ORDER_ID")
    @JsonBackReference(value = "test2")
    private List<ProductShipmentManagerEntity> productShipmentDetails;

    @ManyToOne
    @JoinColumn(name = "PO_ID",insertable = false, updatable = false)
    @JsonBackReference(value = "test3")
    private POMasterEntity poMasterEntity;

    @PreUpdate
    public void setModifiedDateCurrent() {
        this.modifiedDate = new Date();
    }

    @PrePersist
    public void setCreatedDateCurrent() {
        this.createdDate =  new Date();
    }
}