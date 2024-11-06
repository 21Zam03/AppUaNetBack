package com.zam.uanet.controllers;

import com.zam.uanet.payload.response.CommentsResponse;
import com.zam.uanet.payload.response.LikesResponse;
import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.payload.response.PostResponse;
import com.zam.uanet.services.PostService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(PostController.API_PATH)
public class PostController {

    public static final String API_PATH = "/api/posts";
    public static final String LIKE_PATH = "/like";
    public static final String COMMENT_PATH = "/comment";

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostResponse> createPost(
            @RequestPart("message") String message,
            @RequestPart(value = "photo", required = false) MultipartFile file
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<>(postService.createPost(email, message, file), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deletePost(@RequestParam String postId) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<>(postService.deletePost(email, new ObjectId(postId)), HttpStatus.OK);
    }

    @GetMapping
    public Page<PostResponse> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        return postService.getPosts(page, size);
    }

    @GetMapping("/person")
    public Page<PostResponse> getAllPostsByPerson(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam String personId) {
        return postService.getPostsByUser(new ObjectId(personId), page, size);
    }

    @PutMapping(PostController.LIKE_PATH+"/give")
    public ResponseEntity<MessageResponse> giveLike(
            @RequestParam() String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<>(postService.giveLike(email, new ObjectId(postId)), HttpStatus.OK);
    }

    @PutMapping(PostController.LIKE_PATH+"/remove")
    public ResponseEntity<MessageResponse> removeLike(
            @RequestParam() String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<>(postService.removeLike(email, new ObjectId(postId)), HttpStatus.OK);
    }

    @GetMapping(PostController.LIKE_PATH)
    public ResponseEntity<List<LikesResponse>> getLikes(@RequestParam String postId) {
        return new ResponseEntity<>(postService.getLikes(new ObjectId(postId)), HttpStatus.OK);
    }

    @PostMapping(PostController.COMMENT_PATH)
    public ResponseEntity<CommentsResponse> makeComment(
            @RequestParam() String postId,
            @RequestParam() String comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<>(postService.makeComment(email, new ObjectId(postId), comment), HttpStatus.OK);
    }

    @DeleteMapping(PostController.COMMENT_PATH)
    public ResponseEntity<MessageResponse> removeComment(
            @RequestParam() String commentId,
            @RequestParam() String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<>(postService.removeComment(email, new ObjectId(commentId), new ObjectId(postId)), HttpStatus.OK);
    }

    @GetMapping(PostController.COMMENT_PATH)
    public ResponseEntity<List<CommentsResponse>> getComments(@RequestParam String postId) {
        return new ResponseEntity<>(postService.getComments(new ObjectId(postId)), HttpStatus.OK);
    }

}
