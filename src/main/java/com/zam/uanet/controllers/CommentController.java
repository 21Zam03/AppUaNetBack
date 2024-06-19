package com.zam.uanet.controllers;

import com.zam.uanet.dtos.CommentDTO;
import com.zam.uanet.entities.CommentEntity;
import com.zam.uanet.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO createComment(@RequestBody CommentEntity comment) {
        return commentService.createComment(comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteComment(@PathVariable(value = "id") ObjectId idComment) {
        return commentService.deleteComment(idComment);
    }

    @GetMapping("/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> findByPost(@PathVariable(value = "id") ObjectId idPost) {
        return commentService.findByPostQuery(idPost);
    }

}
