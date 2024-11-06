package com.zam.uanet.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private String postId;
    private String personId;
    private String personName;
    private String photoProfile;
    private String nickName;
    private String message;
    private LocalDateTime datePublished;
    private String image;
    private String fileType;
    private List<String> likes;
    private List<String> comments;

}
