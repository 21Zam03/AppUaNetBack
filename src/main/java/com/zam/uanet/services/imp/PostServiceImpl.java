package com.zam.uanet.services.imp;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.zam.uanet.dtos.PostDTO;
import com.zam.uanet.entities.CommentEntity;
import com.zam.uanet.entities.PostEntity;
import com.zam.uanet.repositories.CommentRepository;
import com.zam.uanet.repositories.PostRepository;
import com.zam.uanet.services.PostService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;;
import java.util.Arrays;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoClient mongoClient;

    @Override
    public PostEntity createPost(PostEntity postEntity) {
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
            postDTO.setMessage(postEntityList1.get(i).getMessage());
            postDTO.setDatePublished(postEntityList1.get(i).getDatePublished());
            postDTO.setPhoto(postEntityList1.get(i).getPhoto());
            List<String> lista = new ArrayList<>();
            for (int j = 0; j<postEntityList1.get(i).getLikes().size(); j++) {
                lista.add(postEntityList1.get(i).getLikes().get(j).toHexString());
            }
            postDTO.setLikes(lista);
            postDTO.setTipo(postEntityList1.get(i).getTipo());
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
        List<CommentEntity> comment = commentRepository.findByPostQuery(idPost);
        postRepository.deleteById(idPost);
        for (int i=0; i<comment.size(); i++) {
            commentRepository.deleteById(comment.get(i).getIdComment());
        }
        return "Publicacion eliminada";
    }

    @Override
    public List<PostDTO> findByStudentQuery(ObjectId idStudent) {
        List<PostEntity> listPost = postRepository.findByStudentQuery(idStudent);
        List<PostDTO> listPostDto = new ArrayList<>();
        for (int i=0; i<listPost.size(); i++) {
            PostDTO postDTO = new PostDTO();
            postDTO.setIdPost(listPost.get(i).getIdPost().toHexString());
            postDTO.setIdStudent(listPost.get(i).getIdStudent().toHexString());
            postDTO.setMessage(listPost.get(i).getMessage());
            postDTO.setDatePublished(listPost.get(i).getDatePublished());
            postDTO.setPhoto(listPost.get(i).getPhoto());
            postDTO.setTipo(listPost.get(i).getTipo());
            List<String> lista = new ArrayList<>();
            for (int j = 0; j<listPost.get(i).getLikes().size(); j++) {
                lista.add(listPost.get(i).getLikes().get(j).toHexString());
            }
            postDTO.setLikes(lista);
            listPostDto.add(postDTO);
        }
        return listPostDto;
    }

    @Override
    public int getTotalLikesByStudent(ObjectId idStudent) {
        System.out.println("se inicio el metodo");
        MongoCollection<Document> collection = mongoClient.getDatabase("uanetbd").getCollection("posts");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("idStudent", idStudent)),
                Aggregates.project(Projections.fields(Projections.computed("likesCount", new Document("$size", "$likes")))),
                Aggregates.group(new Document("totalLikes", new Document("$sum", "$likesCount")))
        ));
        Document doc = result.first();
        System.out.println(doc);
        if (doc != null) {
            Document totalLikesDoc = (Document) doc.get("_id");
            if (totalLikesDoc != null) {
                return totalLikesDoc.getInteger("totalLikes", 0);
            } else {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int countPostsByStudentId(ObjectId idStudent) {
        MongoCollection<Document> collection = mongoClient.getDatabase("uanetbd").getCollection("posts");
        return (int) collection.countDocuments(Filters.eq("idStudent", idStudent));
    }

    @Override
    public Page<PostDTO> findByIdStudent(ObjectId idStudent, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostEntity> pagePost = postRepository.findByIdStudent(idStudent, pageable);
        List<PostEntity> listPost= pagePost.getContent();
        List<PostDTO> listPostDto = new ArrayList<>();
        for (int i=0; i<listPost.size(); i++) {
            PostDTO postDTO = new PostDTO();
            postDTO.setIdPost(listPost.get(i).getIdPost().toHexString());
            postDTO.setIdStudent(listPost.get(i).getIdStudent().toHexString());
            postDTO.setMessage(listPost.get(i).getMessage());
            postDTO.setDatePublished(listPost.get(i).getDatePublished());
            postDTO.setPhoto(listPost.get(i).getPhoto());
            postDTO.setTipo(listPost.get(i).getTipo());
            List<String> lista = new ArrayList<>();
            for (int j = 0; j<listPost.get(i).getLikes().size(); j++) {
                lista.add(listPost.get(i).getLikes().get(j).toHexString());
            }
            postDTO.setLikes(lista);
            listPostDto.add(postDTO);
        }
        return new PageImpl<>(listPostDto, pageable, listPostDto.size());
    }

    @Override
    public Page<PostDTO> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostEntity> pagePost = postRepository.findAllByOrderByDatePublishedDesc(pageable);
        List<PostEntity> listPost= pagePost.getContent();
        List<PostDTO> listPostDto = new ArrayList<>();

        for (int i=0; i<listPost.size(); i++) {
            PostDTO postDTO = new PostDTO();
            postDTO.setIdPost(listPost.get(i).getIdPost().toHexString());
            postDTO.setIdStudent(listPost.get(i).getIdStudent().toHexString());
            postDTO.setMessage(listPost.get(i).getMessage());
            postDTO.setDatePublished(listPost.get(i).getDatePublished());
            postDTO.setPhoto(listPost.get(i).getPhoto());
            postDTO.setTipo(listPost.get(i).getTipo());
            List<String> lista = new ArrayList<>();
            for (int j = 0; j<listPost.get(i).getLikes().size(); j++) {
                lista.add(listPost.get(i).getLikes().get(j).toHexString());
            }
            postDTO.setLikes(lista);
            listPostDto.add(postDTO);
        }
        return new PageImpl<>(listPostDto, pageable, pagePost.getTotalElements());
    }

    @Override
    public void updatePostsLikes(ObjectId idPost, List<ObjectId> newLikes) {
        MongoCollection<Document> collection = mongoClient.getDatabase("uanetbd").getCollection("posts");
        Document filter = new Document("_id", idPost);
        Document update = new Document("$set", new Document("likes", newLikes));
        collection.updateOne(filter, update);
    }

}
