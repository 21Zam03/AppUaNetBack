package com.zam.uanet.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String idPost;
    private String idStudent;
    private String message;
    private Date datePublished;
    private byte[] photo;
    private List<String> likes;
    private String tipo;
}
