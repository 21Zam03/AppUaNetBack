package com.zam.uanet.services;

import com.zam.uanet.dtos.PostDTO;
import com.zam.uanet.entities.PostEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    public PostEntity createPost(PostEntity postEntity);
    public PostEntity getPost(ObjectId idPost);
    public List<PostDTO> listPost();
    public PostEntity updatePost(PostEntity postEntity);
    public String deletePost(ObjectId idPost);

    public List<PostDTO> findByStudentQuery(ObjectId idStudent);
    public int getTotalLikesByStudent(ObjectId idStudent);
    public int countPostsByStudentId(ObjectId idStudent);
    Page<PostDTO> findByIdStudent(ObjectId idStudent, int page, int size);
    Page<PostDTO> getAllPosts(int page, int size);
    public void updatePostsLikes(ObjectId idPost, List<ObjectId> newLikes);

}
