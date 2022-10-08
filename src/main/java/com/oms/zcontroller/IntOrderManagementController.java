package com.oms.zcontroller;

import com.oms.pojo.*;
import com.oms.pojo.requestPojo.GetOrdersByCustomerAndPONumberRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IntOrderManagementController {
     ResponseEntity<ResponseStructure<CustomerDetailsPojo>> saveNewOrderDetails(@RequestBody CustomerDetailsPojo customerDetails);
     ResponseEntity<ResponseStructure<CustomerDetailsPojo>> updateOrderDetails(@RequestBody CustomerDetailsPojo customerDetails);
     ResponseEntity<Resource> getAllOrdersByCustomerName(@RequestBody CustomerDetailsPojo customerDetails);
    ResponseEntity <ResponseStructure<List<OrderDetailsResponse>>> getAllOrderWithFilter(@RequestBody GetALLOrderFiltersRequest request);
    ResponseEntity<ResponseStructure<List<OrderDetailsResponse>>> getAllOrder();//stopped using
    ResponseEntity<ResponseStructure<List<Customers>>> getAllCustomers();
    ResponseEntity<ResponseStructure<CustomerDetailsResponsePojo>> getAllOrderByCustomerIdAndPONumber( GetOrdersByCustomerAndPONumberRequest request);
    ResponseEntity<ResponseStructure<List<ProductDetails>>> getAllProducts();
    ResponseEntity<ResponseStructure<List<PODetails>>> getAllPOList();
    ResponseEntity<ResponseStructure<List<PODetails>>> getPODetails(@RequestParam("poNumber")String poNumber);


}
