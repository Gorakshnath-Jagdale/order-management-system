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
@Table(schema = "oms", name = "PO_MASTER")
public class POMasterEntity {

    @Id
    @Column(name = "PO_NUMBER", nullable = false)
    private String poNumber;

    @Column(name = "PO_DATE", nullable = false)
    private Date poDate;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "PO_STATUS", nullable = false)
    private String orderStatus;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private float totalAmount;

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
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "PO_NUMBER",insertable = false, updatable = false)
//    @JsonBackReference(value = "test")
//    private List<ProductOrderManagerEntity> productOrderManagerEntity;


    @PreUpdate
    public void setModifiedDateCurrent() {
        this.modifiedDate = new Date();
    }
    @PrePersist
    public void setCreatedDateCurrent() {
        this.createdDate =  new Date();
    }
}
