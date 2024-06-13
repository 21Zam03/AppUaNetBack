package com.zam.uanet.services;

import com.zam.uanet.dtos.CommentDTO;
import com.zam.uanet.entities.CommentEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface CommentService {

    public CommentEntity createComment(CommentEntity comment);
    public CommentDTO getComment(ObjectId idComment);
    public List<CommentEntity> listComment();
    public CommentDTO updateComment(CommentEntity comment);
    public String deleteComment(ObjectId idComment);
    public List<CommentDTO> findByPostQuery(ObjectId idPost);

}
