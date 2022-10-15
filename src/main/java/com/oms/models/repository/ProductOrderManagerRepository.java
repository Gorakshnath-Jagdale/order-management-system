package com.oms.models.repository;

import com.oms.models.ProductOrderManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderManagerRepository extends JpaRepository<ProductOrderManagerEntity,Long> {
    List<ProductOrderManagerEntity> findByCustomerIdAndPoNumberIgnoreCase(Long customerId, String poNumber);



}
