package com.zam.uanet.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@Data
public class CommentEntity {

    private ObjectId idComment;
    private String comment;

}
