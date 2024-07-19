package com.zam.uanet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastMessageEntity {

    private String senderId;
    private String text;
    private Date createdAt;

}
