package com.oms.models.repository;

import com.oms.models.ProductOrderManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderManagerRepository extends JpaRepository<ProductOrderManagerEntity,Long> {
    boolean existsByIdAndPoMasterEntity_UserLevel(Long id, int userLevel);
//    List<ProductOrderManagerEntity> findByPoNumberIgnoreCase(String poNumber);

    //List<ProductOrderManagerEntity> findByCustomerId(Long customerId);




}
