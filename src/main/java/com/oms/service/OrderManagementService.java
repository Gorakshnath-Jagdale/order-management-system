package com.oms.service;

import com.oms.dto.Requester;
import com.oms.dto.requests.PODetails;
import com.oms.dto.responses.PODetailAsList;
import com.oms.mapper.NewCustomerMapper;
import com.oms.mapper.PODetailsMapper;
import com.oms.mapper.ResponseMapper;
import com.oms.mapper.getAllOrderByCustomerIdAndPONumberMapper;
import com.oms.mapper.response.ProductOrderMapper;
import com.oms.models.POMasterEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.models.repository.*;
import com.oms.pojo.requestPojo.GetOrdersByCustomerAndPONumberRequest;
import com.oms.service.util.ExcelGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final CustomerDetailsRepository customerDetailsRepository;
    private final ResponseMapper responseMapper;
    private final NewCustomerMapper newCustomerMapper;
    private final ExcelGeneratorService excelGeneratorService;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductOrderManagerRepository productOrderManagerRepository;
    private final ProductShipmentManagerRepository productShipmentManagerRepository;
    private final POMasterRepository poMasterRepository;
    private final PODetailsMapper poDetailsMapper;
    private final getAllOrderByCustomerIdAndPONumberMapper getAllOrderByCustomerIdAndPONumberMapper;
    private final ProductOrderMapper productOrderMapper;

    @Transactional
    public PODetails saveNewOrderDetails(PODetails poDetails, Requester requester) throws Exception {
        var customerDetails = poDetails.getCustomerDetailsEntity();
        var poOrders = productOrderMapper.productOrderListToProductOrderManagerEntityList(poDetails.getProductOrderManagerEntity());
        if (customerDetails == null || CollectionUtils.isEmpty(poOrders) /*|| poDetails.getId()!=null*/ || poDetails.getPoNumber() == null) {
            throw new Exception("Invalid request");
        }
        //IF PO ID IS NULL THEN ITS NEW ORDERS
        var po = updatePODetails(poDetails.getId(), poDetails.getPoNumber(), poDetails.getOrderStatus(), poDetails.getPoDate(), customerDetails.getId(), poDetails.getTotalAmount(), requester);
        poOrders.forEach(
                order ->
                {
                    order.setPoId(po.getId());
                    order.setCustomerId(customerDetails.getId());
                    if (order.getId() == null) {
                        order.setCreatedBy(String.valueOf(requester.getUserId()));
                    } else {
                        var orderPersisted = productOrderManagerRepository.findById(order.getId());
                        if (orderPersisted.isPresent()) {
                            var test = orderPersisted.get();
                            order.setPoId(test.getPoId());
                            order.setCreatedDate(test.getCreatedDate());
                            order.setCreatedBy(test.getCreatedBy());
                            order.setModifiedBy(String.valueOf(requester.getUserId()));
                        }
                    }
                }
        );
        var savedOrders=productOrderManagerRepository.saveAll(poOrders);
        poDetails.setProductOrderManagerEntity(productOrderMapper.productOrderEntityListToProductOrderManagerList(savedOrders));
        poDetails.setId(po.getId());
        return poDetails;
    }


    private POMasterEntity updatePODetails(Long poId, String poNumber, String status, Date poDate, long customerId, float totalAmount, Requester requester) {
        POMasterEntity po;
        if (poId == null) {
            po = new POMasterEntity();
            po.setUserLevel(requester.getUserLevel());
            po.setCreatedBy(String.valueOf(requester.getUserId()));
        } else {
            po = poMasterRepository.getById(poId);
            po.setModifiedBy(String.valueOf(requester.getUserId()));
        }
        po.setOrderStatus(status);
        po.setPoDate(poDate);
        po.setPoNumber(poNumber.trim());
        po.setCustomerId(customerId);
        po.setTotalAmount(totalAmount);

        return poMasterRepository.save(po);
    }

//    private List<ProductOrderManagerEntity> SaveOrUpdateShipments(List<ProductOrderManagerEntity> orderManagerEntities) {
//        return productOrderManagerRepository.saveAll(orderManagerEntities);
//    }
//
//    @Transactional
//    public CustomerDetailsPojo updateOrderDetails(CustomerDetailsPojo customerDetails) throws Exception {
//        if (customerDetails.getId() == null) {
//            throw new Exception("ID should not be empty.");
//        } else {
//            return responseMapper.customerDetailsPojoMapper(customerDetailsRepository.save(responseMapper.customerDetailsEntityMapper(customerDetails)));
//        }
//
//    }
//
//
//    public ByteArrayResource getReports(GetExcelRequest request) throws IOException {
//        List<ProductShipmentManagerEntity> productShipmentsByCustomer = new ArrayList<>();
//        if(!StringUtils.isEmpty(request.getPoNumber()))
//        {
//            var poOrders=orderManagerRepository.findByPoNumberIgnoreCase(request.getPoNumber());
//            List<ProductShipmentManagerEntity> finalProductShipmentsByCustomer = productShipmentsByCustomer;
//            poOrders.forEach(order-> finalProductShipmentsByCustomer.addAll(order.getProductShipmentDetails()));
//            if(!request.isGetCompleteOrders()) productShipmentsByCustomer=  finalProductShipmentsByCustomer.stream().filter(x->x.getSupplierDeliveryDate()==null).collect(Collectors.toList());
//        }else {
//
//            if (request.isSingleCustomer()) {
//                if (request.isGetCompleteOrders()) {
//                    productShipmentsByCustomer = productShipmentManagerRepository.findByCustomerDetails_IdOrderBySupplierDeliveryDate(request.getCustomerList().get(0));
//                } else {
//                    productShipmentsByCustomer = productShipmentManagerRepository.findByCustomerDetails_IdAndSupplierDeliveryDateNull(request.getCustomerList().get(0));
//                }
//            } else {
//                if (!request.isGetCompleteOrders()) {
//                    productShipmentsByCustomer = productShipmentManagerRepository.findBySupplierDeliveryDateNull();
//                } else {
//                    productShipmentsByCustomer = productShipmentManagerRepository.findByOrderByCustomerDetails_CustomerNameAscSupplierDeliveryDateDesc();
//                }
//            }
//        }
//        return excelGeneratorService.getOrderDetailsExcel(productShipmentsByCustomer, request.isSingleCustomer());
//    }
//
//    public List<ProductOrderManagerPojo> getAllOrder() {
//        return getAllOrderByCustomerIdAndPONumberMapper.productOrderManagerEntityToProductOrderManagerPojoList(orderManagerRepository.findAll());
//    }
//
////    public List<OrderDetailsResponse> getAllOrderWithFilter(GetALLOrderFiltersRequest request) {
////
////       return null;//responseMapper.orderListMapper(orderManagerRepository.findByCustomerDetails_IdIsOrInvoiceNoContainingIgnoreCaseOrMfgItemNoContainingIgnoreCaseOrCustomerPartNoContainingIgnoreCaseOrPoDateBetween(request.customerId,request.invoice,request.manufacturer,request.customerMFGItemNo,request.fromPODate,request.toPODate));
////    }
//
//    public List<Customers> getAllCustomers() {
//        return customerDetailsRepository.findCustomerList();
//    }
//
//    public List<ProductDetails> getAllProducts() {
//        return responseMapper.productDetailsMapper(productDetailsRepository.findAll());
//    }
//
//    public List<PODetails> getAllPOList() {
//        List<PODetails> list = new ArrayList<>();
//        poMasterRepository.findAll().forEach(po -> {
//
//            list.add(poDetailsMapper.poDetailsPOJOMapper(po, customerDetailsRepository.findCustomerNameOnly(po.getCustomerId())));
//        });
//        return list;
//    }
//
    public PODetails getAllOrderByCustomerIdAndPONumber(GetOrdersByCustomerAndPONumberRequest request,Requester requester) throws Exception {
var poEntity=poMasterRepository.findByPoNumberIgnoreCaseAndCustomerIdAndUserLevel(request.getPoNumber(),request.getCustomerId(),requester.getUserLevel());
      if(poEntity.isPresent())
      {
          return poDetailsMapper.poDetailsPOJOMapper(poEntity.get());
      }
      else
      {
          throw new Exception("Record not found !!");
      }

    }

    public List<PODetailAsList> getAllPurchaseOrder(Requester request) {
return poMasterRepository.getPoDetailsByUserLevel(request.getUserLevel());
    }
}
