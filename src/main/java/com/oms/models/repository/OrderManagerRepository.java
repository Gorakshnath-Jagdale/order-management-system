package com.oms.models.repository;

import com.oms.models.ProductOrderManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderManagerRepository extends JpaRepository<ProductOrderManagerEntity,Long> {
//    @Query("select o from OrderManagerEntity o " +
//            "where o.customerDetails.id = ?1 or upper(o.invoiceNo) like upper(?2) or upper(o.mfgItemNo) like upper(?3) or upper(o.customerPartNo) like upper(?4) or o.poDate between ?5 and ?6")
//    List<OrderManagerEntity> findByCustomerDetails_IdIsOrInvoiceNoContainsIgnoreCaseOrMfgItemNoLikeIgnoreCaseOrCustomerPartNoLikeIgnoreCaseOrPoDateBetween(Long id, String invoiceNo, String mfgItemNo, String customerPartNo, Date poDateStart, Date poDateEnd);

//    @Query("select o from OrderManagerEntity o " +
//            "where o.customerDetails.id = ?1 or upper(o.invoiceNo) like upper(concat('%', ?2, '%')) or upper(o.mfgItemNo) like upper(concat('%', ?3, '%')) or upper(o.customerPartNo) like upper(concat('%', ?4, '%')) or o.poDate between ?5 and ?6")
//    List<ProductOrderManagerEntity> findByCustomerDetails_IdIsOrInvoiceNoContainingIgnoreCaseOrMfgItemNoContainingIgnoreCaseOrCustomerPartNoContainingIgnoreCaseOrPoDateBetween(Long id, String invoiceNo, String mfgItemNo, String customerPartNo, Date poDateStart, Date poDateEnd);

//    @Query("select o from OrderManagerEntity o " +
//            "where o.customerDetails.id = :request.customerId " +
//            "or upper(o.invoiceNo) like upper(:request.invoice) " +
//            "or upper(o.mfgItemNo) like upper(:request.manufacturer) " +
//            "or upper(o.customerPartNo) like upper(:request.customerMFGItemNo) " +
//            "or o.poDate between :request.fromPODate and :request.toPODate")
//    List<OrderManagerEntity> findByCustomerDetails_IdIsOrInvoiceNoLikeIgnoreCaseOrMfgItemNoLikeIgnoreCaseOrCustomerPartNoLikeIgnoreCaseOrPoDateBetween(@Param("request") GetALLOrderFiltersRequest request);

}
//    private Long customerId;
//    private String invoice;
//    private String manufacturer;
//    private Long customerMFGItemNo;
//    private Date fromPODate;
//    private Date toPODate;