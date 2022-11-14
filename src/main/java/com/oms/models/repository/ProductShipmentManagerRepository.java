package com.oms.models.repository;

import com.oms.models.ProductShipmentManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProductShipmentManagerRepository extends JpaRepository<ProductShipmentManagerEntity,Long> {
//    List<ProductShipmentManagerEntity> findByOrderByCustomerDetails_CustomerNameAscSupplierDeliveryDateDesc();
//    List<ProductShipmentManagerEntity> findByCustomerDetails_IdAndSupplierDeliveryDateNull(Long id);
//    List<ProductShipmentManagerEntity> findByCustomerDetails_IdOrderBySupplierDeliveryDate(Long id);
  // 3 List<ProductShipmentManagerEntity> findBySupplierDeliveryDateNull();







}
