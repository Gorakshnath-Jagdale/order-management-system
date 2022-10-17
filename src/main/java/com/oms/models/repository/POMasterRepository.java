package com.oms.models.repository;

import com.oms.dto.responses.PODetailAsList;
import com.oms.models.POMasterEntity;
//import com.oms.pojo.PODetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface POMasterRepository extends JpaRepository<POMasterEntity,Long> {
//    boolean existsByPoNumberIsIgnoreCase(String poNumber);
    @Query("select new com.oms.dto.responses.PODetailAsList(p.id, p.poNumber, p.poDate, p.orderStatus, p.totalAmount,p.customerId,p.customerDetailsEntity.customerName) from POMasterEntity p where p.userLevel = ?1")
    List<PODetailAsList> getPoDetailsByUserLevel(int userLevel);

    List<POMasterEntity> findByCustomerIdAndUserLevelOrderByPoDateDesc(Long customerId, int userLevel);

    Optional<POMasterEntity> findByPoNumberIgnoreCaseAndCustomerIdAndUserLevel(String poNumber, Long customerId, int userLevel);


}
