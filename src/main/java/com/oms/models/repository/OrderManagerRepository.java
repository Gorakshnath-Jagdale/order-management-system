package com.oms.models.repository;

import com.oms.models.ProductOrderManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderManagerRepository extends JpaRepository<ProductOrderManagerEntity,Long> {
    List<ProductOrderManagerEntity> findByPoNumberIgnoreCase(String poNumber);


}
//    private Long customerId;
//    private String invoice;
//    private String manufacturer;
//    private Long customerMFGItemNo;
//    private Date fromPODate;
//    private Date toPODate;