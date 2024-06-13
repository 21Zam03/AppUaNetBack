package com.zam.uanet.dtos;

import com.zam.uanet.entities.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    private CommentEntity comment;
    private String idPost;

}
