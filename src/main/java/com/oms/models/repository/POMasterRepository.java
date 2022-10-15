package com.oms.models.repository;

import com.oms.models.POMasterEntity;
import com.oms.pojo.PODetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface POMasterRepository extends JpaRepository<POMasterEntity,String> {
    boolean existsByPoNumberIsIgnoreCase(String poNumber);
    @Query("select new com.oms.pojo.PODetails( p.poNumber, p.poDate, p.orderStatus, p.totalAmount) from POMasterEntity p where p.poNumber = ?1")
    PODetails getPoDetailsByPoNumber(String poNumber);
}
