package com.zam.uanet.services;

import com.zam.uanet.payload.response.CommentsResponse;
import com.zam.uanet.payload.response.LikesResponse;
import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.payload.response.PostResponse;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface PostService {

    public PostResponse createPost(String email, String message, MultipartFile file) throws Exception;
    public Page<PostResponse> getPosts(int page, int size);
    public Page<PostResponse> getPostsByUser(ObjectId personId, int page, int size);
    public MessageResponse deletePost(String email, ObjectId postId) throws IOException;
    public MessageResponse giveLike(String email, ObjectId postId);
    public MessageResponse removeLike(String email, ObjectId postId);
    public List<LikesResponse> getLikes(ObjectId postId);
    public CommentsResponse makeComment(String email, ObjectId postId, String comment);
    public MessageResponse removeComment(String email, ObjectId commentId, ObjectId postId);
    public List<CommentsResponse> getComments(ObjectId postId);
}
