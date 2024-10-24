package com.zam.uanet.collections;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "permissions")
@Data
public class PermissionCollection {

    @Id
    private ObjectId id;
    private String name;

}
