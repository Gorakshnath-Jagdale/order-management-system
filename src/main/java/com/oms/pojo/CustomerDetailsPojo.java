package com.oms.pojo;

import com.oms.models.OrderManagerEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CustomerDetailsPojo {

    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private List<OrderManagerEntity> customerOrders;
}


