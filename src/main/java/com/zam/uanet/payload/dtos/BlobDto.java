package com.zam.uanet.payload.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlobDto {

    private String mediaLink;
    private String url;
    private Long size;

}
