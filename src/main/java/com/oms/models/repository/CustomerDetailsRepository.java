package com.oms.models.repository;

import com.oms.models.CustomerDetailsEntity;
import com.oms.pojo.CustomerDetailsResponsePojo;
import com.oms.pojo.Customers;
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
    @Query("select new com.oms.pojo.Customers(c.id,c.customerName,c.customerEmail,c.customerContact,c.customerAddress) from CustomerDetailsEntity c")
    List<Customers> findCustomerList();
    @Query("select new com.oms.pojo.Customers(c.id,c.customerName,c.customerEmail,c.customerContact,c.customerAddress) from CustomerDetailsEntity c where c.id = ?1")
    Customers findCustomer(Long id);

    @Query("select new com.oms.pojo.Customers(c.id,c.customerName,c.customerEmail,c.customerContact,c.customerAddress) from CustomerDetailsEntity c where c.id = ?1")
    Customers findCustomerName(Long customerId);
    @Query("select new com.oms.pojo.CustomerDetailsResponsePojo(c.id,c.customerName,c.customerEmail,c.customerContact,c.customerAddress) from CustomerDetailsEntity c where c.id = ?1")
    CustomerDetailsResponsePojo findCustomerDetails(Long customerId);

//    @Query("select c.customerName from CustomerDetailsEntity c where c.id = ?1")
//    String findCustomerNameOnly(Long customerId);
//    boolean existsByIdAndCustomerNameIgnoreCaseAndCustomerEmailIgnoreCaseAndCustomerAddressIgnoreCaseAndCustomerContactIgnoreCase(Long id, String customerName, String customerEmail, String customerAddress, String customerContact);
//
////    @Query("select c from CustomerDetailsEntity c inner join c.customerOrders customerOrders " +
////            "where c.id = ?1 and upper(customerOrders.poNumber) = upper(?2)")
//    CustomerDetailsEntity findByIdAndCustomerOrders_PoNumberIgnoreCase(Long id, String poNumber);
//
//    CustomerDetailsEntity findByIdAndCustomerOrders_PoNumberEqualsIgnoreCase(Long id, String poNumber);

}
