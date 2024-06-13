package com.zam.uanet.controllers;

import com.zam.uanet.dtos.PostDTO;
import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.entities.PostEntity;
import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
            @RequestPart("message") String message,
            @RequestPart("datePublished") String datePublished,
            @RequestPart("likes") String likes,
            @RequestPart("type") String tipo,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        PostEntity postEntity = new PostEntity();
        postEntity.setIdStudent(new ObjectId(idStudent));
        postEntity.setMessage(message);
        postEntity.setLikes(Integer.valueOf(likes));
        postEntity.setTipo(tipo);
        if (photo != null) {
            postEntity.setPhoto(photo.getBytes());
        } else {
            postEntity.setPhoto(null); // O manejar la ausencia de foto según tu lógica de negocio
        }
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
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            @RequestPart("likes") String likes,
            @RequestPart("type") String tipo
    ) throws IOException {
        PostEntity postEntity = new PostEntity();
        postEntity.setIdPost(new ObjectId(idPost));
        postEntity.setIdStudent(new ObjectId(idStudent));
        postEntity.setMessage(message);
        postEntity.setTipo(tipo);
        if (photo != null) {
            postEntity.setPhoto(photo.getBytes());
        } else {
            postEntity.setPhoto(null); // O manejar la ausencia de foto según tu lógica de negocio
        }
        postEntity.setLikes(Integer.valueOf(likes));
        return postService.updatePost(postEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deletePost(@PathVariable(value = "id") ObjectId idPost) {
        return postService.deletePost(idPost);
    }

    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PostDTO> findByStudent(@PathVariable(value = "id") ObjectId idStudent) {
        return postService.findByStudentQuery(idStudent);
    }



}
