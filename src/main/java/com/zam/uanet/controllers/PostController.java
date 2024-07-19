package com.zam.uanet.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zam.uanet.dtos.LikesDto;
import com.zam.uanet.dtos.PostDTO;
import com.zam.uanet.entities.PostEntity;
import com.zam.uanet.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            @RequestPart("likes") String likes,
            @RequestPart("type") String tipo,
            @RequestPart("datePublished") String datePublished,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException, ParseException {
        PostEntity postEntity = new PostEntity();
        postEntity.setIdStudent(new ObjectId(idStudent));
        postEntity.setMessage(message);
        postEntity.setLikes(new ArrayList<>());
        postEntity.setTipo(tipo);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = dateFormat.parse(datePublished);
        postEntity.setDatePublished(fecha);
        if (photo != null) {
            postEntity.setPhoto(photo.getBytes());
        } else {
            postEntity.setPhoto(null); // O manejar la ausencia de foto según tu lógica de negocio
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectId> lista = objectMapper.readValue(likes, new TypeReference<List<ObjectId>>() {});
        postEntity.setLikes(lista);
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
    ) throws IOException, ParseException {
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
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectId> lista = objectMapper.readValue(likes, new TypeReference<List<ObjectId>>() {});
        postEntity.setLikes(lista);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = dateFormat.parse(datePublished);
        postEntity.setDatePublished(fecha);
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

    @GetMapping("/likes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Integer findLikesByStudent(@PathVariable(value = "id") ObjectId idStudent) {
        return postService.getTotalLikesByStudent(idStudent);
    }

    @GetMapping("/countPosts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Integer getcountPostsByStudentId(@PathVariable(value = "id") ObjectId idStudent) {
        return postService.countPostsByStudentId(idStudent);
    }

    @GetMapping("/studentPageable/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostDTO> findByStudentIdPageable(
            @PathVariable(value = "id") ObjectId idStudent,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return postService.findByIdStudent(idStudent, page, size);
    }

    @GetMapping("/allPageable")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostDTO> findAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return postService.getAllPosts(page, size);
    }

    @PutMapping("/likes")
    @ResponseStatus(HttpStatus.OK)
    public void updatePostsLikes(@RequestBody LikesDto likesDto) {
        postService.updatePostsLikes(likesDto.getIdPost(), likesDto.getLikes());
    }

}
