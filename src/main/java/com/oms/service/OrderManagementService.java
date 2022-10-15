package com.oms.service;

import com.oms.mapper.NewCustomerMapper;
import com.oms.mapper.PODetailsMapper;
import com.oms.mapper.ResponseMapper;
import com.oms.mapper.getAllOrderByCustomerIdAndPONumberMapper;
import com.oms.models.POMasterEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.models.ProductShipmentManagerEntity;
import com.oms.models.repository.*;
import com.oms.pojo.*;
import com.oms.pojo.requestPojo.GetExcelRequest;
import com.oms.pojo.requestPojo.GetOrdersByCustomerAndPONumberRequest;
import com.oms.service.util.ExcelGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final CustomerDetailsRepository customerDetailsRepository;
    private final ResponseMapper responseMapper;
    private final NewCustomerMapper newCustomerMapper;
    private final ExcelGeneratorService excelGeneratorService;
    private final OrderManagerRepository orderManagerRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductOrderManagerRepository productOrderManagerRepository;
    private final ProductShipmentManagerRepository productShipmentManagerRepository;
    private final POMasterRepository poMasterRepository;
    private final PODetailsMapper poDetailsMapper;
    private final getAllOrderByCustomerIdAndPONumberMapper getAllOrderByCustomerIdAndPONumberMapper;

    @Transactional
    public CustomerDetailsPojo saveNewOrderDetails(CustomerDetailsPojo customerDetails) throws Exception {

        //If customer is old with no new changes - save orders Only then shifts
        //if customer is old with new changes - update customer then save orders then shifts
        //if customer is new save customer then orders and then shifts

        //if order is old with new changes update order then save or update shifts
        // in case of update operation

        if (customerDetailsRepository.existsByIdAndCustomerNameIgnoreCaseAndCustomerEmailIgnoreCaseAndCustomerAddressIgnoreCaseAndCustomerContactIgnoreCase(
                customerDetails.getId(),
                customerDetails.getCustomerName(),
                customerDetails.getCustomerEmail(),
                customerDetails.getCustomerAddress(),
                customerDetails.getCustomerContact()

        )) {
            // start order save flow - no need to update customer
            var savedOrders = SaveOrUpdateShipments(customerDetails.getCustomerOrders(), customerDetails.getId());
            customerDetails.setCustomerOrders(savedOrders);
            updatePODetails(customerDetails.getPoNumber(), customerDetails.getPoStatus(), customerDetails.getPoDate(), customerDetails.getId(), customerDetails.getTotalAmount());
            return customerDetails;
        } else {
            var updatedOrSavedCustomer = customerDetailsRepository.save(newCustomerMapper.customerDetailsEntityMapper(customerDetails));
            var savedOrders = SaveOrUpdateShipments(customerDetails.getCustomerOrders(), updatedOrSavedCustomer.getId());
            updatedOrSavedCustomer.setCustomerOrders(savedOrders);
            updatePODetails(customerDetails.getPoNumber(), customerDetails.getPoStatus(), customerDetails.getPoDate(), updatedOrSavedCustomer.getId(), customerDetails.getTotalAmount());
            return responseMapper.customerDetailsPojoMapper(updatedOrSavedCustomer);
        }

    }

    private void updatePODetails(String poNumber, String status, Date poDate, long customerId, float totalAmount) {
        if (!poMasterRepository.existsByPoNumberIsIgnoreCase(poNumber.trim())) {
            var po = new POMasterEntity();
            po.setOrderStatus(status);
            po.setPoDate(poDate);
            po.setPoNumber(poNumber.trim());
            po.setCustomerId(customerId);
            po.setTotalAmount(totalAmount);
            poMasterRepository.save(po);
        } else {
            var po = poMasterRepository.getById(poNumber.trim().trim());
            po.setOrderStatus(status);
            po.setPoDate(poDate);
            po.setPoNumber(poNumber.trim());
            po.setCustomerId(customerId);
            po.setTotalAmount(totalAmount);
            poMasterRepository.save(po);
        }
    }

    private List<ProductOrderManagerEntity> SaveOrUpdateShipments(List<ProductOrderManagerEntity> orderManagerEntities, Long customerId) {


        var saveMyOrders = new ArrayList<ProductOrderManagerEntity>();
//            orderManagerEntities.forEach(order->{
//              if(order.getId()!=null)
//              {
//                  var orderUpdate=productOrderManagerRepository.findById(order.getId()).orElseThrow();
//                  orderUpdate.getProductShipmentDetails().addAll(order.getProductShipmentDetails());
//                  saveMyOrders.add(orderUpdate);
//              }else
//              {
//                 saveMyOrders.add(order);
//              }
//            }
//
//            );

        orderManagerEntities.forEach(order -> {
            order.setCustomerId(customerId);
            order.setProductId(order.getMfgItemNumber().getId());//if this Id is null then we can add that product in productDetails table.. future
            order.getProductShipmentDetails().forEach(shipment -> {
                shipment.setProductId(order.getMfgItemNumber().getId());
                shipment.setCustomerId(customerId);
            });
        });

        return productOrderManagerRepository.saveAll(orderManagerEntities);
    }

    @Transactional
    public CustomerDetailsPojo updateOrderDetails(CustomerDetailsPojo customerDetails) throws Exception {
        if (customerDetails.getId() == null) {
            throw new Exception("ID should not be empty.");
        } else {
            return responseMapper.customerDetailsPojoMapper(customerDetailsRepository.save(responseMapper.customerDetailsEntityMapper(customerDetails)));
        }

    }


    public ByteArrayResource getReports(GetExcelRequest request) throws IOException {
        List<ProductShipmentManagerEntity> productShipmentsByCustomer = new ArrayList<>();
        if(!StringUtils.isEmpty(request.getPoNumber()))
        {
            var poOrders=orderManagerRepository.findByPoNumberIgnoreCase(request.getPoNumber());
            List<ProductShipmentManagerEntity> finalProductShipmentsByCustomer = productShipmentsByCustomer;
            poOrders.forEach(order-> finalProductShipmentsByCustomer.addAll(order.getProductShipmentDetails()));
            if(!request.isGetCompleteOrders()) productShipmentsByCustomer=  finalProductShipmentsByCustomer.stream().filter(x->x.getSupplierDeliveryDate()==null).collect(Collectors.toList());
        }else {

            if (request.isSingleCustomer()) {
                if (request.isGetCompleteOrders()) {
                    productShipmentsByCustomer = productShipmentManagerRepository.findByCustomerDetails_IdOrderBySupplierDeliveryDate(request.getCustomerList().get(0));
                } else {
                    productShipmentsByCustomer = productShipmentManagerRepository.findByCustomerDetails_IdAndSupplierDeliveryDateNull(request.getCustomerList().get(0));
                }
            } else {
                if (!request.isGetCompleteOrders()) {
                    productShipmentsByCustomer = productShipmentManagerRepository.findBySupplierDeliveryDateNull();
                } else {
                    productShipmentsByCustomer = productShipmentManagerRepository.findByOrderByCustomerDetails_CustomerNameAscSupplierDeliveryDateDesc();
                }
            }
        }
        return excelGeneratorService.getOrderDetailsExcel(productShipmentsByCustomer, request.isSingleCustomer());
    }

    public List<ProductOrderManagerPojo> getAllOrder() {
        return getAllOrderByCustomerIdAndPONumberMapper.productOrderManagerEntityToProductOrderManagerPojoList(orderManagerRepository.findAll());
    }

//    public List<OrderDetailsResponse> getAllOrderWithFilter(GetALLOrderFiltersRequest request) {
//
//       return null;//responseMapper.orderListMapper(orderManagerRepository.findByCustomerDetails_IdIsOrInvoiceNoContainingIgnoreCaseOrMfgItemNoContainingIgnoreCaseOrCustomerPartNoContainingIgnoreCaseOrPoDateBetween(request.customerId,request.invoice,request.manufacturer,request.customerMFGItemNo,request.fromPODate,request.toPODate));
//    }

    public List<Customers> getAllCustomers() {
        return customerDetailsRepository.findCustomerList();
    }

    public List<ProductDetails> getAllProducts() {
        return responseMapper.productDetailsMapper(productDetailsRepository.findAll());
    }

    public List<PODetails> getAllPOList() {
        List<PODetails> list = new ArrayList<>();
        poMasterRepository.findAll().forEach(po -> {

            list.add(poDetailsMapper.poDetailsPOJOMapper(po, customerDetailsRepository.findCustomerNameOnly(po.getCustomerId())));
        });
        return list;
    }

    public CustomerDetailsResponsePojo getAllOrderByCustomerIdAndPONumber(GetOrdersByCustomerAndPONumberRequest request) {

        var y = customerDetailsRepository.findByIdAndCustomerOrders_PoNumberEqualsIgnoreCase(request.getCustomerId(), request.getPoNumber());
        y.setCustomerOrders(y.getCustomerOrders().stream()
                .filter(x -> x.getPoNumber().equalsIgnoreCase(request.getPoNumber())).collect(Collectors.toList()));
        var response = getAllOrderByCustomerIdAndPONumberMapper.responseMapper(y);
        var po = poMasterRepository.getPoDetailsByPoNumber(request.getPoNumber());
        response.setPoNumber(po.getPoNumber());
        response.setPoDateString(po.getPoDate());
        response.setPoStatus(po.getOrderStatus());
        response.setTotalAmount(po.getTotalAmount());
        return response;
    }
}
