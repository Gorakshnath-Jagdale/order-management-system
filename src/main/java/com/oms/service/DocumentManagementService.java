package com.oms.service;

import com.oms.dto.Requester;
import com.oms.models.repository.POMasterRepository;
import com.oms.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class DocumentManagementService {
    private final POMasterRepository poMasterRepository;
    private Path storageLocation;

    @Autowired
    public DocumentManagementService(POMasterRepository poMasterRepository) throws Exception {
        this.poMasterRepository = poMasterRepository;
        this.storageLocation=Paths.get(Constants.POStatus.UPLOAD_FOLDER).toAbsolutePath().normalize();
//        try{
//            Files.createDirectories(this.storageLocation);
//        } catch (IOException e) {
//           throw new Exception("Can not create folder to store files");
//        }
    }

    public Resource loadFileResource(String fileName,Long customerId, Requester request) throws Exception {
//        String documentName=poMasterRepository.findByUserLevelAndPoNumberIgnoreCaseAndCustomerId(request.getUserLevel(),poNumber,customerId);
//
//       if(documentName==null)
//       {
//           throw new Exception("File not found");
//       }

       // this.storageLocation = Paths.get("C:\\Users\\GORAKSHNATH\\Documents\\Custom Office Templates").toAbsolutePath().normalize();
        Path filePath = storageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new Exception("File not found");
        }
    }

    public String uploadFile(MultipartFile file,Long poId) {
//Noramlize file name
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        try{
            //cheke it the file's name contains invalid characters
            if (fileName.contains("..")){
            throw new Exception("sorry! filename contains invalid path sequence " + fileName);
            }
           // this.storageLocation = Paths.get("C:\\Users\\GORAKSHNATH\\Documents\\Custom Office Templates").toAbsolutePath().normalize();
            Path filePath = storageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
            var getPO=poMasterRepository.getById(poId);
            getPO.setPoDocumentName(fileName);
            poMasterRepository.save(getPO);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    public Stream<Path> loadAllDocuments() throws Exception {
        try {
            return Files.walk(this.storageLocation, 1)
                    .filter(path -> !path.equals(this.storageLocation))
                    .map(this.storageLocation::relativize);
        }
        catch (IOException e) {
            throw new Exception("Failed to read stored files", e);
        }

    }

}
