package com.zam.uanet.collections;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Data
@Builder
public class FileCollection {

    @Id
    private ObjectId fileId;
    private String fileName;
    private String description;
    private Long fileSize;
    private String extension;
    private String url;
    private Boolean isActive;

}
