package com.oms.zcontroller;

import com.oms.execeptions.OMSError;
import com.oms.pojo.CustomerDetailsPojo;
import com.oms.pojo.ResponseStructure;
import com.oms.service.OrderManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderManagementController {

    private final OrderManagementService orderManagementService;

    @PostMapping(value = "/saveOrder",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<CustomerDetailsPojo>> saveNewOrderDetails(@RequestBody CustomerDetailsPojo customerDetails) {
        var response = new ResponseStructure<CustomerDetailsPojo>();
        try{
            response.setResult(orderManagementService.updateOrderDetails(customerDetails));
        }catch (Exception e)
        {
            response.setError(new OMSError("WENT-WRONG",e.getMessage()));
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

    @PostMapping(value = "/getExcel",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> getExcel(@RequestBody CustomerDetailsPojo customerDetails) {
        var response = new ResponseStructure<CustomerDetailsPojo>();
        String filename = "tutorials.xlsx";

        try{
            InputStreamResource file = new InputStreamResource(orderManagementService.test(customerDetails));

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



}
