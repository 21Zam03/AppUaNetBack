package com.zam.uanet.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsResponse {

    private String commentId;
    private String personId;
    private String picturePhoto;
    private String fullName;
    private String message;

}
