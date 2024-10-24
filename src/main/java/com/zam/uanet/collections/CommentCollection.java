package com.zam.uanet.collections;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@Data
@Builder
public class CommentCollection {

    @Id
    private ObjectId commentId;
    private ObjectId personId;
    private String comment;

}
