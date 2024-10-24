package com.zam.uanet.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikesResponse {

    private String personId;
    private String picturePhoto;
    private String fullName;
    private String nickName;

}
