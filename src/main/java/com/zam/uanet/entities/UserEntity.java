package com.zam.uanet.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class UserEntity {

    @Id
    private ObjectId idUser;
    private String email;
    private String password;
    private String rol;

}
