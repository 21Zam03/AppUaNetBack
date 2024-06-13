package com.zam.uanet.services.imp;

import com.zam.uanet.dtos.CommentDTO;
import com.zam.uanet.entities.CommentEntity;
import com.zam.uanet.exceptions.BadRequestException;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.repositories.CommentRepository;
import com.zam.uanet.repositories.PostRepository;
import com.zam.uanet.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentEntity createComment(CommentEntity comment) {
        if(comment == null) {
            log.warn("El comentario es nulo");
            throw new BadRequestException("El el comentario es nulo");
        }
        log.info("Se creo el comentario con exito");
        return commentRepository.save(comment);
    }

    @Override
    public CommentDTO getComment(ObjectId idComment) {
        if(idComment == null) {
            log.warn("El id del comentario es nulo");
            throw new BadRequestException("El id del comentario es nulo");
        }
        CommentEntity comment = commentRepository.findById(idComment)
                .orElseThrow(()-> {
                    log.warn("Comentario no encontrado con el id: {}", idComment);
                    return new NotFoundException("Comentario no encontrado con el id: "+idComment);
                });
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setIdComment(comment.getIdComment().toHexString());
        commentDTO.setComment(comment.getComment());
        commentDTO.setLike(comment.isLike());
        log.info("Se obtuvo el usuario con el id: {}", commentDTO.getIdComment());
        return commentDTO;
    }

    @Override
    public List<CommentEntity> listComment() {
        return commentRepository.findAll();
    }

    @Override
    public CommentDTO updateComment(CommentEntity comment) {
        if (comment.getIdComment() == null) {
            log.warn("El id del comentario a actualizar es nulo");
            throw new BadRequestException("El id del comentario a actualizar es nulo");
        }
        CommentEntity commentEntity = commentRepository.findById(comment.getIdComment())
                .orElseThrow(()-> {
                    log.warn("Comentario a actualizar no fue encontrado con el id: {}", comment.getIdComment());
                    return new NotFoundException("Comentario a actualizar no fue encontrado con el id: "+comment.getIdComment());
                });
        CommentEntity commentUpdated = commentRepository.save(comment);
        log.info("Se actualizo el comentario con el id: {}", commentUpdated.getIdComment());
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setIdComment(commentUpdated.getIdComment().toHexString());
        commentDTO.setComment(commentUpdated.getComment());
        commentDTO.setLike(commentUpdated.isLike());
        return commentDTO;
    }

    @Override
    public String deleteComment(ObjectId idComment) {
        if(idComment == null) {
            log.warn("El id del comentario a eliminar es nulo");
            throw new BadRequestException("El id del comentario a eliminar es nulo");
        }
        CommentEntity commentEntity = commentRepository.findById(idComment)
                .orElseThrow(()-> {
                    log.warn("Comentario a eliminar no fue encontrado con el id: {}", idComment);
                    return new NotFoundException("Comentario a eliminar no fue encontrado con el id: "+idComment);
                });
        commentRepository.delete(commentEntity);
        log.info("Se elimino el comentario con el id: {}", commentEntity.getIdComment());
        return "Eliminacion con exito!";
    }

    @Override
    public List<CommentDTO> findByPostQuery(ObjectId idPost) {
        List<CommentEntity> listaComment = commentRepository.findByPostQuery(idPost);
        List<CommentDTO> listaCommentDTO = new ArrayList<>();
        for (int i=0; i<listaComment.size(); i++) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setIdComment(listaComment.get(i).getIdComment().toHexString());
            commentDTO.setIdStudent(listaComment.get(i).getIdStudent().toHexString());
            commentDTO.setIdPost(listaComment.get(i).getIdPost().toHexString());
            commentDTO.setComment(listaComment.get(i).getComment());
            commentDTO.setLike(listaComment.get(i).isLike());
            listaCommentDTO.add(commentDTO);
        }
        return listaCommentDTO;
    }

}
