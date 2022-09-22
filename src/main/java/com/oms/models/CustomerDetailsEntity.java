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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long id;
    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String customerName;
    @Column(name = "CUSTOMER_EMAIL", nullable = false)
    private String customerEmail;
    @Column(name = "CUSTOMER_ADDRESS", nullable = false)
    private String customerAddress;
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

    @OneToMany(mappedBy = "customerId")
    private List<OrderManagerEntity> customerOrders;
}


