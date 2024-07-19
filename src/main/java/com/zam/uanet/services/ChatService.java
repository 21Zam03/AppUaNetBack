package com.zam.uanet.services;

import com.zam.uanet.dtos.ChatDTO;
import com.zam.uanet.entities.ChatEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface ChatService {

    public ChatDTO createChat(ChatEntity chatEntity);
    public void deleteChat(ObjectId idChat);
    List<ChatDTO> findByStudentIdOrderByCreatedAtDesc(ObjectId studentId);
}
