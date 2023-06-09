package com.oms.models.repository;

import com.oms.models.POMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface  POMasterRepository extends JpaRepository<POMasterEntity,Long>, JpaSpecificationExecutor<POMasterEntity> {

    POMasterEntity findByPoNumberIgnoreCase(String poNumber);

    long countByOrderStatusIgnoreCase(String orderStatus);

    long countByCreatedByInAndOrderStatus(Collection<Integer> createdBIES, String orderStatus);


    long countByOrderStatusAndCreatedByInAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateNull(String orderStatus,Collection<Integer> createdBIES);

    long countByCreatedByInAndOrderStatusAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateLessThanEqualAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceNoIsIgnoreCaseAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceDateNull(Collection<Integer> createdBIES,String orderStatus, Date supplierDeliveryDate, String invoiceNo);

    List<POMasterEntity> findByOrderStatusInAndCustomerIdAndProductOrderManagerEntity_ProductId(Collection<String> orderStatuses, Long customerId, Long productId);
    List<POMasterEntity> findByOrderStatusInAndCustomerId(Collection<String> orderStatuses, Long customerId);

    List<POMasterEntity> findByOrderStatusInAndProductOrderManagerEntity_ProductId(Collection<String> orderStatuses, Long productId);

    @Query("select p.poDocumentName from POMasterEntity p where upper(p.poNumber) = upper(?1) and p.customerId = ?2")
    String findByPoNumberIgnoreCaseAndCustomerId(String poNumber, Long customerId);

}
