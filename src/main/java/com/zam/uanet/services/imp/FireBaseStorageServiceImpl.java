package com.zam.uanet.services.imp;

import com.google.api.core.ApiFuture;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.zam.uanet.payload.dtos.BlobDto;
import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.services.FireBaseStorageService;
import com.zam.uanet.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class FireBaseStorageServiceImpl implements FireBaseStorageService {

    @Override
    public BlobDto uploadFile(MultipartFile file, String index, String referenceId) throws Exception {
        InputStream inputStream = file.getInputStream();

        //String fileName = FileUtil.getFileName(file);

        String filePath = index + referenceId;

        BlobInfo blobInfo = BlobInfo.newBuilder(StorageClient.getInstance().bucket().getName(), filePath)
                .setContentType(file.getContentType())
                .build();

        Blob blob = StorageClient.getInstance().bucket().create(blobInfo.getName(), inputStream, blobInfo.getContentType());

        String encodedFilePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8);
        String url = "https://firebasestorage.googleapis.com/v0/b/" + StorageClient.getInstance().bucket().getName() + "/o/" +
                encodedFilePath + "?alt=media";

        BlobDto blobDto = new BlobDto(blob.getMediaLink(), url, blob.getSize());

        log.info("The file was uploaded successfully");
        return blobDto;
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        return new byte[0];
    }

    @Override
    public MessageResponse deleteFile(String referenceId, String index) throws IOException {
        String filePath = index + referenceId;
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        boolean deleted = storage.delete(StorageClient.getInstance().bucket().getName(), filePath);

        if (deleted) {
            return new MessageResponse("File was deleted successfully");
        } else {
            return new MessageResponse("There was an error deleting the file");
        }
    }
}
