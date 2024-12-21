package com.zam.uanet.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoUserRequest {

    private String roleId;
    private String district;
    private String career;
    private String genre;

}
