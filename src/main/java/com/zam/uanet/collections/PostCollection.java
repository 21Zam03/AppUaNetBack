package com.zam.uanet.collections;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "posts")
@Data
@Builder
public class PostCollection {

    @Id
    private ObjectId postId;
    private ObjectId personId;
    private String message;
    private LocalDateTime datePublished;
    private String image;
    private List<ObjectId> likes;
    private List<ObjectId> comments;
    //private List<ObjectId> saves;
    //private String tipo;

}
