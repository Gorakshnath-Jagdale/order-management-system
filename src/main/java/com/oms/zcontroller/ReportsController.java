package com.oms.zcontroller;

import com.oms.dto.RequestStructure;
import com.oms.dto.ResponseStructure;
import com.oms.dto.requests.ReportsFilterRequest;
import com.oms.dto.responses.ReportsFilterResponse;
import com.oms.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportsService reportsService;

    @PostMapping("getReports")
    ResponseEntity<ResponseStructure<List<ReportsFilterResponse>>> getFilteredOrderDetails(@RequestBody  RequestStructure<ReportsFilterRequest> request)
    {
        var response=new ResponseStructure<List<ReportsFilterResponse>>();

        response.setResult(reportsService.getFilteredOrderDetails(request));

        return ResponseEntity.ok(response);
    }
}
