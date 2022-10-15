package com.oms.zcontroller;

import com.oms.execeptions.OMSError;
import com.oms.pojo.*;
import com.oms.pojo.requestPojo.GetExcelRequest;
import com.oms.pojo.requestPojo.GetOrdersByCustomerAndPONumberRequest;
import com.oms.service.OrderManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class OrderManagementController implements IntOrderManagementController {

    private final OrderManagementService orderManagementService;

    @PostMapping(value = "/saveOrder",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<ResponseStructure<CustomerDetailsPojo>> saveNewOrderDetails(@RequestBody CustomerDetailsPojo customerDetails) {
        var response = new ResponseStructure<CustomerDetailsPojo>();
        try{
            response.setResult(orderManagementService.saveNewOrderDetails(customerDetails));
            response.setError(new OMSError("",""));
        }catch (Exception e)
        {
            response.setError(new OMSError("WENT-WRONG",String.valueOf(e)));
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "/updateOrder",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<CustomerDetailsPojo>> updateOrderDetails(@RequestBody CustomerDetailsPojo customerDetails) {
        var response = new ResponseStructure<CustomerDetailsPojo>();
       try{
           response.setResult(orderManagementService.updateOrderDetails(customerDetails));
       }catch (Exception e)
       {
           response.setError(new OMSError("WENT-WRONG",e.getMessage()));
       }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/getReports",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByteArrayResource> getReports(@RequestBody GetExcelRequest customerDetails) {
        var response = new ResponseStructure<CustomerDetailsPojo>();
        String filename = "Report-"+new Date()+".xlsx";

        try{
            var file=orderManagementService.getReports(customerDetails);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);

        }catch (Exception e)
        {
            response.setError(new OMSError("WENT-WRONG",e.getMessage()));
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE)).body(null);
    }


//    @Override
//    @PostMapping(value = "/getAllOrderWithFilter",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseStructure<List<OrderDetailsResponse>>> getAllOrderWithFilter(GetALLOrderFiltersRequest request) {
//        var response = new ResponseStructure<List<OrderDetailsResponse>>();
//        try{
//            response.setResult(orderManagementService.getAllOrderWithFilter(request));
//        }catch (Exception e)
//        {
//            response.setError(new OMSError("WENT-WRONG",e.getMessage()));
//        }
//        return ResponseEntity.ok(response);
//    }

    @Override
    @GetMapping(value = "/getAllOrder",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<ProductOrderManagerPojo>>> getAllOrder() {

        var response = new ResponseStructure<List<ProductOrderManagerPojo>>();
        try{response.setResult(orderManagementService.getAllOrder());
        }catch (Exception e)
        {
            response.setError(new OMSError("WENT-WRONG",e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(value = "/getAllCustomers",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<Customers>>> getAllCustomers() {
        var response = new ResponseStructure<List<Customers>>();
        try{response.setResult(orderManagementService.getAllCustomers());
        }catch (Exception e)
        {
            response.setError(new OMSError("WENT-WRONG",e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping(
            value = "/getPODetails",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<CustomerDetailsResponsePojo>> getAllOrderByCustomerIdAndPONumber(@RequestBody GetOrdersByCustomerAndPONumberRequest request) {
        var response = new ResponseStructure<CustomerDetailsResponsePojo>();
        try{response.setResult(orderManagementService.getAllOrderByCustomerIdAndPONumber(request));
        }catch (Exception e)
        {
            response.setError(new OMSError("WENT-WRONG",e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(value = "/getAllItemsList",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<ProductDetails>>> getAllProducts() {
        var response = new ResponseStructure<List<ProductDetails>>();
        try{response.setResult(orderManagementService.getAllProducts());
        }catch (Exception e)
        {
            response.setError(new OMSError("WENT-WRONG",e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(value = "/getPOList",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<PODetails>>> getAllPOList() {
        var response = new ResponseStructure<List<PODetails>>();
        try{response.setResult(orderManagementService.getAllPOList());
        }catch (Exception e)
        {
            response.setError(new OMSError("WENT-WRONG",e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<PODetails>>> getPODetails(String poNumber) {
        return null;
    }
}
    