package com.oms.service;

import com.oms.mapper.NewCustomerMapper;
import com.oms.mapper.PODetailsMapper;
import com.oms.mapper.ResponseMapper;
import com.oms.mapper.getAllOrderByCustomerIdAndPONumberMapper;
import com.oms.models.POMasterEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.models.repository.*;
import com.oms.pojo.*;
import com.oms.pojo.requestPojo.GetOrdersByCustomerAndPONumberRequest;
import com.oms.service.util.ExcelGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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


if(customerDetailsRepository.existsByIdAndCustomerNameIgnoreCaseAndCustomerEmailIgnoreCaseAndCustomerAddressIgnoreCaseAndCustomerContactIgnoreCase(
        customerDetails.getId(),
        customerDetails.getCustomerName(),
        customerDetails.getCustomerEmail(),
        customerDetails.getCustomerAddress(),
        customerDetails.getCustomerContact()

))
{
    // start order save flow - no need to update customer
   var savedOrders= SaveOrUpdateShipments(customerDetails.getCustomerOrders(),customerDetails.getId());
    customerDetails.setCustomerOrders(savedOrders);
    updatePODetails(customerDetails);
    return customerDetails;
}
else
{
    var updatedOrSavedCustomer=customerDetailsRepository.save(newCustomerMapper.customerDetailsEntityMapper(customerDetails));
    var savedOrders= SaveOrUpdateShipments(customerDetails.getCustomerOrders(),updatedOrSavedCustomer.getId());
    updatedOrSavedCustomer.setCustomerOrders(savedOrders);
    updatePODetails(responseMapper.customerDetailsPojoMapper(updatedOrSavedCustomer));
    return responseMapper.customerDetailsPojoMapper(updatedOrSavedCustomer);
}

        }

    private void updatePODetails(CustomerDetailsPojo customerDetails) {
        if(!poMasterRepository.existsByPoNumberIsIgnoreCase(customerDetails.getPoNumber().trim()))
        {
            var po=new POMasterEntity();
            po.setOrderStatus("NEW");
            po.setPoDate(customerDetails.getPoDate());
            po.setPoNumber(customerDetails.getPoNumber().trim());
            po.setCustomerId(customerDetails.getId());
            po.setTotalAmount(customerDetails.getTotalAmount());
            poMasterRepository.save(po);
        }else
        {
           var poDetails= poMasterRepository.getById(customerDetails.getPoNumber().trim());
            poDetails.setOrderStatus(customerDetails.getPoStatus());
            poDetails.setPoDate(customerDetails.getPoDate());
            poDetails.setPoNumber(customerDetails.getPoNumber().trim());
            poDetails.setCustomerId(customerDetails.getId());
            poDetails.setTotalAmount(customerDetails.getTotalAmount());
           poMasterRepository.save(poDetails);
        }
    }

    private List<ProductOrderManagerEntity> SaveOrUpdateShipments(List<ProductOrderManagerEntity> orderManagerEntities,Long customerId)
        {


            var saveMyOrders =new ArrayList<ProductOrderManagerEntity>();
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

            orderManagerEntities.forEach(order->{
                order.setCustomerId(customerId);
                order.getProductShipmentDetails().forEach(shipment-> {
                    shipment.setProductId(order.getMfgItemNumber().getId());
                    shipment.setCustomerId(customerId);
                });
            });

           ;

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


    public InputStream getAllOrdersByCustomerName(CustomerDetailsPojo customerDetails) throws IOException {
       var customerOrderDetails= customerDetailsRepository.findByCustomerNameIsIgnoreCaseOrCustomerEmailIsIgnoreCaseOrCustomerAddressIsIgnoreCase(customerDetails.getCustomerName(),customerDetails.getCustomerEmail(),customerDetails.getCustomerAddress());

       List<ProductOrderManagerEntity> orderList=new ArrayList<>();
        customerOrderDetails.forEach(x-> orderList.addAll(x.getCustomerOrders()));


        return excelGeneratorService.getOrderDetailsExcel(responseMapper.orderListMapper(orderList),customerOrderDetails.size()==1);
    }

    public List<OrderDetailsResponse> getAllOrder() {
        return responseMapper.orderListMapper(orderManagerRepository.findAll());
    }

    public List<OrderDetailsResponse> getAllOrderWithFilter(GetALLOrderFiltersRequest request) {

       return null;//responseMapper.orderListMapper(orderManagerRepository.findByCustomerDetails_IdIsOrInvoiceNoContainingIgnoreCaseOrMfgItemNoContainingIgnoreCaseOrCustomerPartNoContainingIgnoreCaseOrPoDateBetween(request.customerId,request.invoice,request.manufacturer,request.customerMFGItemNo,request.fromPODate,request.toPODate));
    }

    public List<Customers> getAllCustomers() {
        return customerDetailsRepository.findCustomerList();
    }

    public List<ProductDetails> getAllProducts() {
       return responseMapper.productDetailsMapper(productDetailsRepository.findAll());
    }

    public List<PODetails> getAllPOList() {
        List<PODetails> list=new ArrayList<>();
        poMasterRepository.findAll().forEach(po->{

            list.add( poDetailsMapper.poDetailsPOJOMapper(po,customerDetailsRepository.findCustomerNameOnly(po.getCustomerId()) ));
        });
return list;
    }

    public CustomerDetailsResponsePojo getAllOrderByCustomerIdAndPONumber(GetOrdersByCustomerAndPONumberRequest request) {
    var result=customerDetailsRepository.findByIdAndCustomerOrders_PoNumberIgnoreCase(request.getCustomerId(),request.getPoNumber());
        return getAllOrderByCustomerIdAndPONumberMapper.responseMapper(result);
    }
}
