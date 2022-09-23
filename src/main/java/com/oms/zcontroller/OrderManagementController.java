package com.oms.zcontroller;

import com.oms.pojo.CustomerDetailsPojo;
import com.oms.pojo.ResponseStructure;
import com.oms.pojo.UserDetailsPojo;
import com.oms.service.OrderManagementService;
import lombok.RequiredArgsConstructor;
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
        response.setResult(orderManagementService.saveNewOrderDetails(customerDetails));
        return ResponseEntity.ok(response);
    }
}
