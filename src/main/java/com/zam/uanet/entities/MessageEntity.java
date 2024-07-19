package com.zam.uanet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class MessageEntity {

    @Id
    private ObjectId idMessage;
    private ObjectId idChat;
    private ObjectId senderId;
    private String text;
    private Date createdAt;

}
