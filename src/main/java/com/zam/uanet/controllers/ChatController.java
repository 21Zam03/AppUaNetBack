package com.zam.uanet.controllers;

import com.zam.uanet.dtos.ChatDTO;
import com.zam.uanet.entities.ChatEntity;
import com.zam.uanet.services.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ChatDTO createChat(@RequestBody ChatEntity chatEntity) {
        return chatService.createChat(chatEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable(value = "id") ObjectId idChat) {
        chatService.deleteChat(idChat);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ChatDTO> findByStudentIdOrderByCreatedAtDesc(@PathVariable(value = "id") ObjectId idStudent) {
        return chatService.findByStudentIdOrderByCreatedAtDesc(idStudent);
    }

}
