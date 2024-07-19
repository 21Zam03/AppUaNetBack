package com.zam.uanet.services.imp;

import com.zam.uanet.dtos.MessageDTO;
import com.zam.uanet.entities.MessageEntity;
import com.zam.uanet.exceptions.BadRequestException;
import com.zam.uanet.repositories.MessageRepository;
import com.zam.uanet.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    public MessageRepository messageRepository;

    @Override
    public MessageDTO createMessage(MessageEntity message) {
        if(message == null) {
            log.warn("El message es nulo");
            throw new BadRequestException("El message es nulo");
        }
        System.out.println(message);
        MessageEntity messageEntity = messageRepository.save(message);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setIdMessage(messageEntity.getIdMessage().toHexString());
        messageDTO.setIdChat(messageEntity.getIdChat().toHexString());
        messageDTO.setSenderId(messageEntity.getSenderId().toHexString());
        messageDTO.setText(messageEntity.getText());
        messageDTO.setCreateAt(messageEntity.getCreatedAt());
        log.info("Se creo el mensaje con exito");
        return messageDTO;
    }

    @Override
    public List<MessageDTO> getMessagesByIdChat(ObjectId idChat) {
        List<MessageEntity> list = messageRepository.findByIdChat(idChat);
        List<MessageDTO> listDto = new ArrayList<>();
        for (int i = 0; i<list.size(); i++) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setIdMessage(list.get(i).getIdMessage().toHexString());
            messageDTO.setIdChat(list.get(i).getIdChat().toHexString());
            messageDTO.setSenderId(list.get(i).getSenderId().toHexString());
            messageDTO.setText(list.get(i).getText());
            messageDTO.setCreateAt(list.get(i).getCreatedAt());
            listDto.add(messageDTO);
        }
        return listDto;
    }
}
