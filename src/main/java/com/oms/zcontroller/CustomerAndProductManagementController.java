package com.oms.zcontroller;

import com.oms.dto.RequestStructure;
import com.oms.dto.requests.Customer;
import com.oms.dto.requests.Product;
import com.oms.execeptions.OMSError;
import com.oms.pojo.ResponseStructure;
import com.oms.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/manage/")
@RequiredArgsConstructor
public class CustomerAndProductManagementController {
    private final ManagementService customerManagementService;

    /* SAVE/UPDATE/GET CUSTOMER */
    @PostMapping(value = "/customer/createCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<Customer>> createCustomer(@RequestBody RequestStructure<Customer> request) {
        var response = new ResponseStructure<Customer>();
        try {
            response.setResult(customerManagementService.createCustomer(request.getRequest(), request.getRequester().getUserId()));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "/customer/updateCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<Customer>> updateCustomer(@RequestBody RequestStructure<Customer> request) {
        var response = new ResponseStructure<Customer>();
        try {
            response.setResult(customerManagementService.updateCustomer(request.getRequest(), request.getRequester().getUserId()));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/customer/getCustomer/{customerId}")
    public ResponseEntity<ResponseStructure<Customer>> getCustomer(@PathVariable(name = "customerId") Long customerId) {
        var response = new ResponseStructure<Customer>();
        try {
            response.setResult(customerManagementService.getCustomer(customerId));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "/customer/getCustomers")
    public ResponseEntity<ResponseStructure<List<Customer>>> getCustomers() {
        var response = new ResponseStructure<List<Customer>>();
        try {
            response.setResult(customerManagementService.getCustomers());
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    /* SAVE/UPDATE/GET CUSTOMER END */


    /* SAVE/UPDATE/GET PRODUCTS */

    @PostMapping(value = "/product/createProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<Product>> createProduct(@RequestBody RequestStructure<Product> request) {
        var response = new ResponseStructure<Product>();
        try {
            response.setResult(customerManagementService.createProduct(request.getRequest(), request.getRequester().getUserId()));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "/product/updateProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody RequestStructure<Product> request) {
        var response = new ResponseStructure<Product>();
        try {
            response.setResult(customerManagementService.updateProduct(request.getRequest(), request.getRequester().getUserId()));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/product/getProduct/{productId}")
    public ResponseEntity<ResponseStructure<Product>> getProduct(@PathVariable(name = "productId") Long productId) {
        var response = new ResponseStructure<Product>();
        try {
            response.setResult(customerManagementService.getProduct(productId));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "/product/getProducts")
    public ResponseEntity<ResponseStructure<List<Product>>> getProducts() {
        var response = new ResponseStructure<List<Product>>();
        try {
            response.setResult(customerManagementService.getProducts());
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    /* SAVE/UPDATE/GET PRODUCTS END */

}
