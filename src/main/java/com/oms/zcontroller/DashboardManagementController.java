package com.oms.zcontroller;

import com.oms.dto.Requester;
import com.oms.dto.ResponseStructure;
import com.oms.dto.responses.DashboardResponse;
import com.oms.execeptions.OMSError;
import com.oms.service.DashboardManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardManagementController {
    private final DashboardManagementService dashboardManagementService;

    @PostMapping(value = "/getDashboardStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStructure<DashboardResponse>> completePO(@RequestBody Requester requester) {
        var response = new ResponseStructure<DashboardResponse>();
        try {

            response.setResult(dashboardManagementService.getDashboardStatus(requester));
        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }


}
