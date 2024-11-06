package com.zam.uanet.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSuggestionResponse {

    private String postId;
    private String message;
    private String authorName;
    private Integer likes;
    private Integer comments;

}
