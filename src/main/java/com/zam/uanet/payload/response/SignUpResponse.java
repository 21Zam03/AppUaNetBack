package com.zam.uanet.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SignUpResponse {

    private String personId;
    private String email;
    private String picturePhoto;
    private String fullName;
    private String nickName;
    private List<String> roles;
    private String message;
    private String token;
    private Integer status;

}
