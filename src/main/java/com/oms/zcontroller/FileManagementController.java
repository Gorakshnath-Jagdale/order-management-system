package com.oms.zcontroller;

import com.oms.dto.RequestStructure;
import com.oms.dto.Requester;
import com.oms.dto.ResponseStructure;
import com.oms.dto.requests.PODetails;
import com.oms.pojo.requestPojo.GetExcelRequest;
import com.oms.pojo.requestPojo.GetOrdersByCustomerAndPONumberRequest;
import com.oms.service.DocumentManagementService;
import com.oms.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/document")
public class FileManagementController {

    private final DocumentManagementService documentManagementService;

    @GetMapping(value = "/loadDocument/{fileName:.+}")
    public ResponseEntity<Resource> loadFile(@PathVariable(name = "fileName") String fileName) throws Exception {
        {
            var fileNameSplit=fileName.split("[.]+");
            var fileExtention=fileNameSplit[fileNameSplit.length-1];
            Resource resource = documentManagementService.loadFileResource(fileName,null,null);
            // String contentType=resource.getServletContext().
        var contentType=fileExtention.equalsIgnoreCase("pdf")?"application/pdf":"image/jpg";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION
                            , "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        }
    }

//    @PostMapping("/")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
//
//        //	storageService.store(file);
//
//        return "redirect:/";
//    }
    @PostMapping("/uploadDocument")
    public ResponseEntity<ResponseStructure<String>> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("poId") Long poId) throws Exception {
        {
            var response = new ResponseStructure<String>();
            var resource = documentManagementService.uploadFile(file,poId);
            response.setResult(resource);
            return ResponseEntity.ok(response);

        }
    }

    @PostMapping("/getListOfAllDocuments")
    public ResponseEntity<ResponseStructure<List<String>>> getListOfAllDocuments(@RequestBody Requester requester) throws Exception {
        {
            var response = new ResponseStructure<List<String>>();

            var resourceList= documentManagementService.loadAllDocuments().map(
                            path -> MvcUriComponentsBuilder.fromMethodName(FileManagementController.class,
                                    "serveFile", path.getFileName().toString()).build().toUri().toString())
                    .collect(Collectors.toList());
            response.setResult(resourceList);
            return ResponseEntity.ok(response);

        }
    }


}
