package com.zam.uanet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chats")
public class ChatEntity {

    @Id
    private ObjectId idChat;
    private List<ObjectId> students;
    private Date createdAt;
    private LastMessageEntity lastMessage;

}
