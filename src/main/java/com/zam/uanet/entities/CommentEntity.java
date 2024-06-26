package com.zam.uanet.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@Data
public class CommentEntity {

    @Id
    private ObjectId idComment;
    private ObjectId idStudent;
    private ObjectId idPost;
    private String comment;
    private boolean like;

}
