package com.zam.uanet.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "posts")
@Data
public class PostEntity {

    @Id
    private ObjectId idPost;
    private ObjectId idStudent;
    private String message;
    private Date datePublished;
    private byte[] photo;
    private List<ObjectId> likes;
    private List<ObjectId> saves;
    private String tipo;

}
