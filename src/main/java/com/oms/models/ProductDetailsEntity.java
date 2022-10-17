package com.oms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(schema = "OMS_ADVANCE", name = "PRODUCT_DETAILS")
public class ProductDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long id;
    @Column(name = "MFG_ITEM_NUMBER", nullable = false)
    private String mfgItemNumber;
    @Column(name = "MANUFACTURER", nullable = false)
    private String manufacturer;
    @Column(name = "productDetails", nullable = false)
    private String productDetails;

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

    @OneToMany
    @JoinColumn(name = "PRODUCT_ID",insertable = false, updatable = false)
    @JsonBackReference(value = "test")
    private List<ProductOrderManagerEntity> productOrderManagerEntity;

    @OneToMany
    @JoinColumn(name = "PRODUCT_ID",insertable = false, updatable = false)
    @JsonBackReference(value = "test2")
    private List<ProductShipmentManagerEntity> productShipmentManagerEntity;

    @PreUpdate
    public void setModifiedDateCurrent() {
        this.modifiedDate = new Date();
    }
    @PrePersist
    public void setCreatedDateCurrent() {
        this.createdDate =  new Date();
    }
}
