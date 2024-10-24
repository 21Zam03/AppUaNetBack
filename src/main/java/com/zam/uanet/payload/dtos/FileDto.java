package com.zam.uanet.payload.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDto {

    private String fileName;
    private String description;
    private Long fileSize;
    private String extension;
    private String url;
    private Boolean isActive;

}
