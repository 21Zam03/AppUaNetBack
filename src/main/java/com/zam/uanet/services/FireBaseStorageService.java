package com.zam.uanet.services;

import com.zam.uanet.payload.dtos.BlobDto;
import com.zam.uanet.payload.response.MessageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FireBaseStorageService {

    public BlobDto uploadFile(MultipartFile file, String index, String referenceId) throws Exception;
    byte[] downloadFile(String fileName) throws IOException;
    public MessageResponse deleteFile(String referenceId, String index) throws IOException;

}
