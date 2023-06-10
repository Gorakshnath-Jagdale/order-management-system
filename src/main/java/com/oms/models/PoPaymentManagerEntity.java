package com.oms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(schema = "OMS_ADVANCE", name = "PO_PAYMENT_MANAGER")
public class PoPaymentManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PO_PAYMENT_ID", nullable = false)
    private Long id;

    @Column(name = "PO_ID", nullable = false)
    private Long poId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Column(name = "ORIGINAL_AMOUNT", nullable = false)
    private float originalAmount;
    @Column(name = "PAID_AMOUNT", nullable = false)
    private float paidAmount;

    @Column(name = "DAYS_DUE", nullable = false)
    private int daysDue;

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

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", insertable = false, updatable = false)
    @JsonBackReference
    private CustomerDetailsEntity customerDetails;

    @OneToOne
    @JoinColumn(name = "PO_ID", insertable = false, updatable = false)
    @JsonBackReference
    private POMasterEntity poDetails;

    @PreUpdate
    public void setModifiedDateCurrent() {
        this.modifiedDate = new Date();
    }

    @PrePersist
    public void setCreatedDateCurrent() {
        this.createdDate = new Date();
    }
}
