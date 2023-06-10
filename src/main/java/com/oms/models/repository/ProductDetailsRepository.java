package com.oms.models.repository;

import com.oms.dto.requests.Manufacturer;
import com.oms.models.ProductDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProductDetailsRepository extends JpaRepository<ProductDetailsEntity, Long> {
    List<ProductDetailsEntity> findByManufacturerIgnoreCase(String manufacturer);


    @Query("select distinct new com.oms.dto.requests.Manufacturer(p.manufacturer) from ProductDetailsEntity p")
    Set<Manufacturer> findAllManufacturer();


}
