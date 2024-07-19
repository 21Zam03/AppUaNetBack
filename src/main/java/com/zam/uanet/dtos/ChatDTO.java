package com.zam.uanet.dtos;

import com.zam.uanet.entities.LastMessageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {

    private String idChat;
    private List<String> students;
    private Date createdAt;
    private LastMessageEntity lastMessage;
}
