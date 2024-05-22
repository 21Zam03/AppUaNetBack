package com.zam.uanet.services.imp;

import com.zam.uanet.dtos.PostDTO;
import com.zam.uanet.entities.PostEntity;
import com.zam.uanet.repositories.PostRepository;
import com.zam.uanet.services.PostService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public Date obtenerFecha() {
        LocalDateTime fechaActual = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(fechaActual);
        Date fechaSQL = new Date(timestamp.getTime());
        return fechaSQL;
    }

    @Override
    public PostEntity createPost(PostEntity postEntity) {
        Date fecha = this.obtenerFecha();
        postEntity.setDatePublished(fecha);
        return postRepository.save(postEntity);
    }

    @Override
    public PostEntity getPost(ObjectId idPost) {
        return null;
    }

    @Override
    public List<PostDTO> listPost() {
        List<PostEntity> postEntityList1 = postRepository.findAll();
        List<PostDTO> postEntityList2 = new ArrayList<>();

        for (int i = 0; i<postEntityList1.size(); i++) {
            PostDTO postDTO = new PostDTO();
            postDTO.setIdPost(postEntityList1.get(i).getIdPost().toHexString());
            postDTO.setIdStudent(postEntityList1.get(i).getIdStudent().toHexString());
            postDTO.setIdComments(postEntityList1.get(i).getIdComments());
            postDTO.setMessage(postEntityList1.get(i).getMessage());
            postDTO.setDatePublished(postEntityList1.get(i).getDatePublished());
            postDTO.setPhoto(postEntityList1.get(i).getPhoto());
            postEntityList2.add(postDTO);
        }
        return postEntityList2;
    }

    @Override
    public PostEntity updatePost(PostEntity postEntity) {
        PostEntity post = postRepository.findById(postEntity.getIdPost()).get();
        post = postEntity;
        return postRepository.save(post);
    }

    @Override
    public String deletePost(ObjectId idPost) {
        return "";
    }

}
