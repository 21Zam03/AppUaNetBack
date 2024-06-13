package com.zam.uanet.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String idComment;
    private String idStudent;
    private String idPost;
    private String comment;
    private boolean like;

}
