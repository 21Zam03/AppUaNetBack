package com.zam.uanet.controllers;

import com.zam.uanet.dtos.MessageDTO;
import com.zam.uanet.entities.MessageEntity;
import com.zam.uanet.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDTO> findByIdChat(@PathVariable(value = "id") ObjectId idChat) {
        return messageService.getMessagesByIdChat(idChat);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO createMessage(@RequestBody MessageEntity messageEntity) {
        return messageService.createMessage(messageEntity);
    }

}
