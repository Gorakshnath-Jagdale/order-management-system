package com.oms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(schema = "OMS", name = "CUSTOMER_DETAILS")
public class CustomerDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long id;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "CUSTOMER_EMAIL")
    private String customerEmail;
    @Column(name = "CUSTOMER_ADDRESS")
    private String customerAddress;

    @Column(name = "CUSTOMER_CONTACT")
    private String customerContact;

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

    @JsonBackReference
    @OneToMany(mappedBy = "customerId",fetch = FetchType.EAGER, targetEntity = ProductOrderManagerEntity.class,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//   @JoinColumn(name = "CUSTOMER_ID",referencedColumnName = "CUSTOMER_ID")
    private List<ProductOrderManagerEntity> customerOrders;

//    @JsonBackReference(value = "test2")
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "CUSTOMER_ID",referencedColumnName = "CUSTOMER_ID",insertable = false, updatable = false)
//    private List<ProductShipmentManagerEntity> productShipments;

    @PreUpdate
    public void setModifiedDateCurrent() {
        this.modifiedDate = new Date();
    }
    @PrePersist
    public void setCreatedDateCurrent() {
        this.createdDate =  new Date();
    }
}


