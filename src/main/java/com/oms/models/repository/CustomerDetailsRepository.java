package com.oms.models.repository;

import com.oms.models.CustomerDetailsEntity;
import com.oms.pojo.CustomerDetailsResponsePojo;
import com.oms.pojo.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerDetailsRepository  extends JpaRepository <CustomerDetailsEntity,Long>
{
    @Query("select new com.oms.pojo.Customers(c.id,c.customerName,c.customerEmail,c.customerContact,c.customerAddress) from CustomerDetailsEntity c where c.id = ?1")
    Customers findCustomerName(Long customerId);
    @Query("select new com.oms.pojo.CustomerDetailsResponsePojo(c.id,c.customerName,c.customerEmail,c.customerContact,c.customerAddress) from CustomerDetailsEntity c where c.id = ?1")
    CustomerDetailsResponsePojo findCustomerDetails(Long customerId);

    List<CustomerDetailsEntity> findDistinctByCreatedByInOrderByCustomerNameAsc(Collection<Integer> createdBIES);

    Optional<CustomerDetailsEntity> findByCreatedByInAndId(Collection<Integer> createdBIES, Long id);




}
