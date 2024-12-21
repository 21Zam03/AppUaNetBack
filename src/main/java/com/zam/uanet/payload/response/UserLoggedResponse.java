package com.zam.uanet.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoggedResponse {

    private String personId;
    private String email;
    private String picturePhoto;
    private String fullName;
    private String nickName;
    private List<String> roles;
    private String district;
    private String career;

}
