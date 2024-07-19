package com.zam.uanet.services;

import com.zam.uanet.dtos.MessageDTO;
import com.zam.uanet.entities.MessageEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface MessageService {

    public MessageDTO createMessage(MessageEntity message);
    public List<MessageDTO> getMessagesByIdChat(ObjectId idChat);
}
