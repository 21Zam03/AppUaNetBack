package com.zam.uanet.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "friends")
@Data
public class FriendEntity {

    @Id
    private ObjectId userId;
    private String status;
    private String requestBy;

}
