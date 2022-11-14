package com.oms.models.repository;

import com.oms.dto.responses.PODetailAsList;
import com.oms.models.POMasterEntity;
//import com.oms.pojo.PODetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface  POMasterRepository extends JpaRepository<POMasterEntity,Long> {
    boolean existsByPoNumberIgnoreCaseAndUserLevel(String poNumber, int userLevel);
//    boolean existsByPoNumberIsIgnoreCase(String poNumber);



    @Query("select new com.oms.dto.responses.PODetailAsList(p.id, p.poNumber, p.poDate, p.orderStatus, p.totalAmount,p.customerId,p.customerDetailsEntity.customerName) from POMasterEntity p where p.userLevel = ?1  order by createdDate desc")
    List<PODetailAsList> getPoDetailsByUserLevelOrderByCreatedDateDesc(int userLevel);

    List<POMasterEntity> findByUserLevelIsOrderByCreatedDateDesc(int userLevel);


    List<POMasterEntity> findByCustomerIdAndUserLevelOrderByPoDateDesc(Long customerId, int userLevel);

    Optional<POMasterEntity> findByPoNumberIgnoreCaseAndCustomerIdAndUserLevel(String poNumber, Long customerId, int userLevel);

    POMasterEntity findByPoNumberIgnoreCase(String poNumber);

    List<POMasterEntity> findByCustomerIdOrderByPoDateAsc(Long customerId);

    List<POMasterEntity> findByCustomerIdAndOrderStatusIgnoreCaseOrderByPoDateDesc(Long customerId, String orderStatus);

   // List<POMasterEntity> findAllOrderByPoDateAsc();

    List<POMasterEntity> findByCustomerIdInAndOrderStatusInIgnoreCaseAndUserLevelOrderByPoDateDesc(Collection<Long> customerId, Collection<String> orderStatuses, int userLevel);

    List<POMasterEntity> findByOrderStatusInIgnoreCaseAndUserLevelOrderByPoDateDesc(Collection<String> orderStatuses, int userLevel);

    boolean existsByUserLevelAndId(int userLevel, Long id);

    long countByOrderStatusIgnoreCaseAndUserLevel(String orderStatus, int userLevel);

    long countByOrderStatusAndUserLevelAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateNull(String orderStatus, int userLevel);

    long countByOrderStatusAndUserLevelAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateLessThanEqualAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceNoIsIgnoreCaseAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceDateNull(String orderStatus, int userLevel, Date supplierDeliveryDate, String invoiceNo);


    long countByUserLevelAndOrderStatusIgnoreCaseAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateLessThanAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceDateNullAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceNoNull(int userLevel, String orderStatus, Date supplierDeliveryDate);

    List<POMasterEntity> findByOrderStatusInAndUserLevelAndCustomerIdAndProductOrderManagerEntity_ProductId(Collection<String> orderStatuses, int userLevel, Long customerId, Long productId);
    List<POMasterEntity> findByOrderStatusInAndUserLevelAndCustomerId(Collection<String> orderStatuses, int userLevel, Long customerId);

    List<POMasterEntity> findByOrderStatusInAndUserLevelAndProductOrderManagerEntity_ProductId(Collection<String> orderStatuses, int userLevel, Long productId);
    //List<POMasterEntity> findByOrderStatusInIgnoreCaseAndUserLevelAndCustomerIdOrProductOrderManagerEntity_ProductId(Collection<String> orderStatuses, int userLevel, Long customerId, Long productId);




// DashBoard Counts

    // DashBoard Counts END


}
