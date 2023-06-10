package com.oms.zcontroller;

import com.oms.dto.RequestStructure;
import com.oms.dto.Requester;
import com.oms.dto.ResponseStructure;
import com.oms.dto.requests.*;
import com.oms.dto.responses.PODetailAsList;
import com.oms.execeptions.OMSError;
import com.oms.pojo.requestPojo.GetOrdersByPOIdRequest;
import com.oms.service.OrderManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class OrderManagementController {

    private final OrderManagementService orderManagementService;


    /* SAVE/UPDATE/GET PURCHASE ORDER */

    @PostMapping(value = {"/savePurchaseOrder", "/updatePurchaseOrder"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<PODetails>> savePurchaseOrder(@RequestBody RequestStructure<PODetails> request) {
        var response = new ResponseStructure<PODetails>();
        try {
            /*SAVE AND UPDATE USE SAME SERVICE METHOD */
            response.setResult(orderManagementService.saveNewOrderDetails(request.getRequest(), request.getRequester()));
            response.setError(new OMSError("", ""));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", String.valueOf(e)));
        }
        return ResponseEntity.ok(response);
    }

    //    @PostMapping(value = "/updatePurchaseOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public  ResponseEntity<ResponseStructure<PODetails>> updatePurchaseOrder(@RequestBody RequestStructure<PODetails> request)  {
//        var response = new ResponseStructure<PODetails>();
//        try {
//            /*SAVE AND UPDATE USE SAME SERVICE METHOD */
//            response.setResult(orderManagementService.saveNewOrderDetails(request.getRequest() , request.getRequester()));
//            response.setError(new OMSError("", ""));
//        } catch (Exception e) {
//            response.setError(new OMSError("WENT-WRONG", String.valueOf(e)));
//        }
//        return ResponseEntity.ok(response);
//    }
    @PostMapping(value = "/updateSchedules", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<String>> updateSchedules(@RequestBody RequestStructure<ScheduleUpdateRequest> request) {
        var response = new ResponseStructure<String>();
        try {
            /*SAVE AND UPDATE USE SAME SERVICE METHOD */
            response.setResult(orderManagementService.updateSchedules(request.getRequester(), request.getRequest()));
            response.setFlag(true);
            response.setError(new OMSError("", ""));
        } catch (Exception e) {
            response.setFlag(false);
            response.setError(new OMSError("WENT-WRONG", String.valueOf(e)));
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/deleteOrderItemDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<String>> deleteItemOrderFromPO(@RequestBody RequestStructure<Long> request) {
        var response = new ResponseStructure<String>();
        try {
            /* Delete */
            response.setResult(orderManagementService.deleteItemOrderFromPO(request.getRequest(), request.getRequester()));
            response.setFlag(true);
            response.setError(new OMSError("", ""));
        } catch (Exception e) {
            response.setFlag(false);
            response.setError(new OMSError("WENT-WRONG", String.valueOf(e)));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            value = "/getPurchaseOrder",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<PODetails>> getAllOrderByCustomerIdAndPONumber(@RequestBody RequestStructure<GetOrdersByPOIdRequest> request) {
        var response = new ResponseStructure<PODetails>();
        try {
            response.setResult(orderManagementService.getPurchaseOrderById(request.getRequest(), request.getRequester()));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            value = "/getAllPurchaseOrder",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<PODetailAsList>>> getAllPurchaseOrder(@RequestBody RequestStructure<ReportsFilterRequest> request) {
        var response = new ResponseStructure<List<PODetailAsList>>();
        try {
            response.setResult(orderManagementService.getAllPurchaseOrder(request.getRequest(), request.getRequester()));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    /* SAVE/UPDATE PURCHASE/GET ORDER END */

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

    @PostMapping(value = "/{operation}/{poId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<String>> completePO(@PathVariable(name = "operation") String operation, @PathVariable(name = "poId") Long poId, @RequestBody Requester request) {
        var response = new ResponseStructure<String>();
        try {

            response.setResult(orderManagementService.updateStatus(poId, request, operation));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }
    /* Bulk schedule update */

    @PostMapping(value = "/bulkSupplierDeliveryDateUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<ProductOrderManager>>> bulkSupplierDeliveryDateUpdate(@RequestBody RequestStructure<BulkSupplierDeliveryDateUpdateRequest> request) {
        var response = new ResponseStructure<List<ProductOrderManager>>();

        try {
            response.setResult(orderManagementService.bulkSupplierDeliveryDateUpdate(request));
            response.setFlag(true);
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
            response.setFlag(false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/bulkInvoiceDetailsUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<List<ProductOrderManager>>> bulkInvoiceDetailsUpdate(@RequestBody RequestStructure<BulkInvoiceDetailsUpdateRequest> request) {
        var response = new ResponseStructure<List<ProductOrderManager>>();
        try {

            response.setResult(orderManagementService.bulkInvoiceDetailsUpdate(request));
            response.setFlag(true);
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
            response.setFlag(false);
        }
        return ResponseEntity.ok(response);
    }

}
    