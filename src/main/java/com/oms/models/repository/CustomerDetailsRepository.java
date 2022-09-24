package com.oms.models.repository;

import com.oms.models.CustomerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerDetailsRepository  extends JpaRepository <CustomerDetailsEntity,Long>
{
    @Query("select c from CustomerDetailsEntity c where upper(c.customerName) like upper(?1)")
    Optional<CustomerDetailsEntity> findByCustomerNameLikeIgnoreCase(String customerName);

    List<CustomerDetailsEntity> findByCustomerNameIsIgnoreCaseOrCustomerEmailIsIgnoreCaseOrCustomerAddressIsIgnoreCase(String customerName, String customerEmail, String customerAddress);

    CustomerDetailsEntity findByCustomerNameContainsOrCustomerEmailContainsAndCustomerAddressContains(String customerName, String customerEmail, String customerAddress);






}
