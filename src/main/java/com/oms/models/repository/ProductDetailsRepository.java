package com.oms.models.repository;

import com.oms.models.ProductDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailsRepository extends JpaRepository<ProductDetailsEntity,Long> {
}
