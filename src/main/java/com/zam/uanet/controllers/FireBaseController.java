package com.zam.uanet.controllers;

import com.zam.uanet.services.FireBaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(FireBaseController.API_PATH)
public class FireBaseController {

    public static final String API_PATH = "/api/fireBase";

    public final FireBaseStorageService fireBaseStorageService;

    @Autowired
    public FireBaseController(FireBaseStorageService fireBaseStorageService) {
        this.fireBaseStorageService = fireBaseStorageService;
    }

    @PostMapping(value= "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("file") String index,
            @RequestPart("file") String postReference
    ) throws Exception {
        return new ResponseEntity<>(fireBaseStorageService.uploadFile(file, index, postReference), HttpStatus.OK);
    }

    @GetMapping(value= "/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"")
                .body(fireBaseStorageService.downloadFile(fileName));
    }

    @DeleteMapping(value= "/delete/{fileName}")
    public ResponseEntity<?> deleteFile(
            @PathVariable String referenceId,
            @PathVariable String index) throws IOException {
        return new ResponseEntity<>(fireBaseStorageService.deleteFile(referenceId, index), HttpStatus.OK);
    }

}
