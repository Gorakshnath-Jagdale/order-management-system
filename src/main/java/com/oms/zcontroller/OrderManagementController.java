package com.oms.zcontroller;

import com.oms.dto.RequestStructure;
import com.oms.dto.Requester;
import com.oms.dto.ResponseStructure;
import com.oms.dto.requests.FilteredReportRequest;
import com.oms.dto.responses.PODetailAsList;
import com.oms.execeptions.OMSError;
import com.oms.pojo.*;
import com.oms.pojo.requestPojo.GetExcelRequest;
import com.oms.pojo.requestPojo.GetOrdersByCustomerAndPONumberRequest;
import com.oms.service.OrderManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.oms.dto.requests.PODetails;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class OrderManagementController {

    private final OrderManagementService orderManagementService;


    /* SAVE/UPDATE/GET PURCHASE ORDER */

    @PostMapping(value = "/savePurchaseOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<PODetails>> savePurchaseOrder(@RequestBody RequestStructure<PODetails> request) {
        var response = new ResponseStructure<PODetails>();
        try {
            /*SAVE AND UPDATE USE SAME SERVICE METHOD */
            response.setResult(orderManagementService.saveNewOrderDetails(request.getRequest() , request.getRequester()));
            response.setError(new OMSError("", ""));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", String.valueOf(e)));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/updatePurchaseOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ResponseStructure<PODetails>> updatePurchaseOrder(@RequestBody RequestStructure<PODetails> request)  {
        var response = new ResponseStructure<PODetails>();
        try {
            /*SAVE AND UPDATE USE SAME SERVICE METHOD */
            response.setResult(orderManagementService.saveNewOrderDetails(request.getRequest() , request.getRequester()));
            response.setError(new OMSError("", ""));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", String.valueOf(e)));
        }
        return ResponseEntity.ok(response);
    }


//    @PostMapping(value = "/getPurchaseOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseStructure<CustomerDetailsPojo>> getPurchaseOrder(@RequestBody CustomerDetailsPojo customerDetails) {
//        var response = new ResponseStructure<CustomerDetailsPojo>();
//        try {
//            response.setResult(orderManagementService.updateOrderDetails(customerDetails));
//        } catch (Exception e) {
//            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
//        }
//        return ResponseEntity.ok(response);
//    }
    @PostMapping(
            value = "/getPurchaseOrder",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<PODetails>> getAllOrderByCustomerIdAndPONumber(@RequestBody RequestStructure<GetOrdersByCustomerAndPONumberRequest> request) {
        var response = new ResponseStructure<PODetails>();
        try {
            response.setResult(orderManagementService.getAllOrderByCustomerIdAndPONumber(request.getRequest(),request.getRequester()));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping(
            value = "/getAllPurchaseOrder",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<PODetailAsList>>> getAllPurchaseOrder(@RequestBody Requester request) {
        var response = new ResponseStructure<List<PODetailAsList>>();
        try {
            response.setResult(orderManagementService.getAllPurchaseOrder(request));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    /* SAVE/UPDATE PURCHASE/GET ORDER END */


    /* UPDATE PURCHASE ORDER STATUS */

    /* SAVE UPDATE PURCHASE ORDER STATUS END */


    /* GET REPORTS */
    @PostMapping(value = "/getReports", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByteArrayResource> getReports(@RequestBody RequestStructure<GetExcelRequest> request) {
        var response = new ResponseStructure<CustomerDetailsPojo>();
        String filename = "Report-"+new Date()+".xlsx";

        try {
            var file = orderManagementService.getReports(request.getRequest(),request.getRequester());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);

        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE)).body(null);
    }

    @PostMapping(value = "/getFilteredReport", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<PODetails>>> getFilteredReport(@RequestBody RequestStructure<FilteredReportRequest> request) {
        var response = new ResponseStructure<List<PODetails>>();

        try {
            response.setResult(orderManagementService.getFilteredReport(request));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE)).body(null);
    }
    /* GET REPORTS END */

//
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
//
//
//    @GetMapping(value = "/getAllOrder", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseStructure<List<ProductOrderManagerPojo>>> getAllOrder() {
//
//        var response = new ResponseStructure<List<ProductOrderManagerPojo>>();
//        try {
//            response.setResult(orderManagementService.getAllOrder());
//        } catch (Exception e) {
//            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
//        }
//        return ResponseEntity.ok(response);
//    }
//
//
//

//
//
//    /* General get calls */
//
//
//    @GetMapping(value = "/getAllItemsList", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseStructure<List<ProductDetails>>> getAllProducts() {
//        var response = new ResponseStructure<List<ProductDetails>>();
//        try {
//            response.setResult(orderManagementService.getAllProducts());
//        } catch (Exception e) {
//            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
//        }
//        return ResponseEntity.ok(response);
//    }
//
//
//    @GetMapping(value = "/getPOList", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseStructure<List<PODetails>>> getAllPOList() {
//        var response = new ResponseStructure<List<PODetails>>();
//        try {
//            response.setResult(orderManagementService.getAllPOList());
//        } catch (Exception e) {
//            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
//        }
//        return ResponseEntity.ok(response);
//    }
//
//
//    @GetMapping(value = "/getAllCustomers", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseStructure<List<Customers>>> getAllCustomers() {
//        var response = new ResponseStructure<List<Customers>>();
//        try {
//            response.setResult(orderManagementService.getAllCustomers());
//        } catch (Exception e) {
//            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
//        }
//        return ResponseEntity.ok(response);
//    }


    /* General get calls end */

    @PostMapping(value = "/{operation}/{poId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<String>> completePO(@PathVariable(name = "operation")String operation,@PathVariable(name = "poId")Long poId,@RequestBody Requester request) {
        var response = new ResponseStructure<String>();
        try {

            response.setResult(orderManagementService.updateStatus(poId,request,operation));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

}
    