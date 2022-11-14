package com.oms.zcontroller;

import com.oms.dto.RequestStructure;
import com.oms.dto.Requester;
import com.oms.pojo.requestPojo.GetExcelRequest;
import com.oms.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/document")
public class FileManagementController {

    private final ManagementService managementService;

    @GetMapping(value = "/loadDocument/{poNumber:.+}")
    public ResponseEntity<Resource> loadFile(@PathVariable(name = "poNumber") String poNumber) throws Exception {
        {
            Resource resource = managementService.loadFileResource(poNumber, null);
            // String contentType=resource.getServletContext().
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION
                            , "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        }
    }
}
