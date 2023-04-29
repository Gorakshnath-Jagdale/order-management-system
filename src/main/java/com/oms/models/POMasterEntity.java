package com.oms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(schema = "OMS_ADVANCE", name = "PURCHESE_ORDER_MASTER")
public class POMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PO_ID", nullable = false)
    private Long id;

    @Column(name = "PO_NUMBER", nullable = false)
    private String poNumber;

    @Column(name = "PO_DATE", nullable = false)
    private Date poDate;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "PO_STATUS", nullable = false)
    private String orderStatus;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private double totalAmount;

//    @Column(name = "USER_LEVEL", nullable = false)
//    private int userLevel;

    @Column(name="PO_DOCUMENT_NAME")
    private String poDocumentName;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @OneToMany
    @JoinColumn(name = "po_Id",insertable = false, updatable = false)
    @JsonBackReference(value = "test")
    private List<ProductOrderManagerEntity> productOrderManagerEntity;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID",insertable = false, updatable = false)
    @JsonBackReference(value = "test2")
    private CustomerDetailsEntity customerDetailsEntity;
    @ManyToOne
    @JoinColumn(name = "CREATED_BY",insertable = false, updatable = false)
    @JsonBackReference(value = "userDetailsEntity")
    private UserDetailsEntity userDetailsEntity;


    @PreUpdate
    public void setModifiedDateCurrent() {
        this.modifiedDate = new Date();
    }
    @PrePersist
    public void setCreatedDateCurrent() {
        this.createdDate =  new Date();
    }
}
