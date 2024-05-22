package com.zam.uanet.services;

import com.zam.uanet.dtos.PostDTO;
import com.zam.uanet.entities.PostEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface PostService {

    public PostEntity createPost(PostEntity postEntity);
    public PostEntity getPost(ObjectId idPost);
    public List<PostDTO> listPost();
    public PostEntity updatePost(PostEntity postEntity);
    public String deletePost(ObjectId idPost);

}
