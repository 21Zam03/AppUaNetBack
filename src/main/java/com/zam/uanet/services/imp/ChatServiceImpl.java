package com.zam.uanet.services.imp;

import com.zam.uanet.dtos.ChatDTO;
import com.zam.uanet.entities.ChatEntity;
import com.zam.uanet.entities.MessageEntity;
import com.zam.uanet.exceptions.BadRequestException;
import com.zam.uanet.repositories.ChatRepository;
import com.zam.uanet.repositories.MessageRepository;
import com.zam.uanet.services.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public ChatDTO createChat(ChatEntity chatEntity) {
        if(chatEntity == null) {
            log.warn("El chat es nulo");
            throw new BadRequestException("El chat es nulo");
        }
        ChatEntity chat = chatRepository.save(chatEntity);
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setIdChat(chat.getIdChat().toHexString());
        List<String> lista = new ArrayList<>();
        for (int i = 0; i<chat.getStudents().size(); i++) {
            lista.add(chat.getStudents().get(i).toHexString());
        }
        chatDTO.setStudents(lista);
        log.info("Se creo el chat con exito");

        MessageEntity message = new MessageEntity();
        message.setIdChat(chat.getIdChat());
        message.setSenderId(new ObjectId(chat.getLastMessage().getSenderId()));
        message.setText(chat.getLastMessage().getText());
        message.setCreatedAt(chat.getLastMessage().getCreatedAt());
        messageRepository.save(message);
        return chatDTO;
    }

    @Override
    public void deleteChat(ObjectId idChat) {
        chatRepository.deleteById(idChat);
    }

    @Override
    public List<ChatDTO> findByStudentIdOrderByCreatedAtDesc(ObjectId studentId) {
        List<ChatEntity> list = chatRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
        List<ChatDTO> listDTO = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setIdChat(list.get(i).getIdChat().toHexString());
            List<String> lista = new ArrayList<>();
            for (int j=0; j<list.get(i).getStudents().size(); j++) {
                lista.add(list.get(i).getStudents().get(j).toHexString());
            }
            chatDTO.setStudents(lista);
            chatDTO.setCreatedAt(list.get(i).getCreatedAt());
            chatDTO.setLastMessage(list.get(i).getLastMessage());
            listDTO.add(chatDTO);
        }
        return listDTO;
    }

}
