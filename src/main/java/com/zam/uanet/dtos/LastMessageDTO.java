package com.zam.uanet.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastMessageDTO {

    private String idLastMessage;
    private String senderId;
    private String text;
    private Date createdAt;

}
