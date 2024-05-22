package com.zam.uanet.controllers;

import com.zam.uanet.dtos.PostDTO;
import com.zam.uanet.entities.PostEntity;
import com.zam.uanet.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PostEntity createPost(
            @RequestPart("idStudent") String idStudent,
            @RequestPart("datePublished") String datePublished,
            @RequestPart("photo") MultipartFile photo
    ) throws IOException {
        PostEntity postEntity = new PostEntity();
        postEntity.setIdStudent(new ObjectId(idStudent));
        postEntity.setPhoto(photo.getBytes());
        return postService.createPost(postEntity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDTO> listPost() {
        return postService.listPost();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PostEntity updatePost(
            @RequestPart("idPost") String idPost,
            @RequestPart("idStudent") String idStudent,
            @RequestPart("datePublished") String datePublished,
            @RequestPart("message") String message,
            @RequestPart("photo") MultipartFile photo
    ) throws IOException {
        PostEntity postEntity = new PostEntity();
        postEntity.setIdPost(new ObjectId(idPost));
        postEntity.setIdStudent(new ObjectId(idStudent));
        postEntity.setMessage(message);
        postEntity.setPhoto(photo.getBytes());
        return postService.updatePost(postEntity);
    }
}
