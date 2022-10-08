package com.oms.models.repository;

import com.oms.models.POMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POMasterRepository extends JpaRepository<POMasterEntity,String> {
    boolean existsByPoNumberIsIgnoreCase(String poNumber);

}
