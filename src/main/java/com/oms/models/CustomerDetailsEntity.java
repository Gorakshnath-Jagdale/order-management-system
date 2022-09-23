package com.oms.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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

    @OneToMany(targetEntity = OrderManagerEntity.class,cascade = CascadeType.PERSIST)
   @JoinColumn(name = "CUSTOMER_ID",referencedColumnName = "CUSTOMER_ID")
    private List<OrderManagerEntity> customerOrders;
}


