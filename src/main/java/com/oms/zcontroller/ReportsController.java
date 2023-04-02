package com.oms.zcontroller;

import com.oms.dto.RequestStructure;
import com.oms.dto.ResponseStructure;
import com.oms.dto.requests.ReportsFilterRequest;
import com.oms.dto.responses.ReportsFilterResponse;
import com.oms.execeptions.OMSError;
import com.oms.pojo.CustomerDetailsPojo;
import com.oms.pojo.requestPojo.GetExcelRequest;
import com.oms.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportsService reportsService;

    @PostMapping("/getReports")
    ResponseEntity<ResponseStructure<List<ReportsFilterResponse>>> getFilteredOrderDetails(@RequestBody  RequestStructure<ReportsFilterRequest> request)
    {
        var response=new ResponseStructure<List<ReportsFilterResponse>>();
try{
        response.setResult(reportsService.getFilteredOrderDetails(request));
    } catch (Exception e) {
        response.setError(new OMSError("WENT-WRONG", e.getMessage()));
    }
        return ResponseEntity.ok(response);


    }

    /* GET REPORTS */
    @PostMapping(value = "/getExcelReports", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByteArrayResource> getExcelReports(@RequestBody  RequestStructure<ReportsFilterRequest> request){
        var response = new ResponseStructure<CustomerDetailsPojo>();
        var tag=request.getRequest().isConsolidate()?"-Consolidated":"";
        String filename = "Report"+tag+"-"+new Date()+".xlsx";
        try {
            var file = reportsService.getExcelReport(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);

        } catch (Exception e) {
            response.setError(new OMSError("WENT-WRONG", e.getMessage()));
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE)).body(null);
    }
}
