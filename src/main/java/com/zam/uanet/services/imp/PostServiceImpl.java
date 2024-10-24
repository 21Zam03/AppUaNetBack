package com.zam.uanet.services.imp;

import com.mongodb.client.MongoClient;
import com.zam.uanet.collections.CommentCollection;
import com.zam.uanet.collections.PostCollection;
import com.zam.uanet.collections.PersonCollection;
import com.zam.uanet.collections.UserCollection;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.payload.dtos.BlobDto;
import com.zam.uanet.payload.response.CommentsResponse;
import com.zam.uanet.payload.response.LikesResponse;
import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.payload.response.PostResponse;
import com.zam.uanet.repositories.CommentRepository;
import com.zam.uanet.repositories.PostRepository;
import com.zam.uanet.repositories.PersonRepository;
import com.zam.uanet.repositories.UserRepository;
import com.zam.uanet.services.FireBaseStorageService;
import com.zam.uanet.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FireBaseStorageService fireBaseStorageService;
    private final MongoClient mongoClient;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostServiceImpl (PostRepository postRepository, MongoClient mongoClient,
                            FireBaseStorageService fireBaseStorageService, PersonRepository personRepository,
                            UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.mongoClient = mongoClient;
        this.fireBaseStorageService = fireBaseStorageService;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public PostResponse createPost(String email, String message, MultipartFile file) throws Exception {

        UserCollection loggedUser = userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User not found");
            return new NotFoundException("User doest not exist");
        });

        PersonCollection personCollection = personRepository.findByUserId(loggedUser.getIdUser()).orElseThrow(() -> {
            return new NotFoundException("Person not found");
        });

        PostCollection postCollection = PostCollection.builder()
                .personId(personCollection.getPersonId())
                .message(message)
                .datePublished(LocalDateTime.now())
                .image(null)
                .likes(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
        PostCollection postCreated = postRepository.save(postCollection);

        if (file != null) {
            BlobDto blobDto = fireBaseStorageService.uploadFile(file, "posts/", postCollection.getPostId().toHexString());
            postCollection.setImage(blobDto.getUrl());
            postCreated = postRepository.save(postCollection);
        }
        log.info("Post was created successfully by {}", personCollection.getFullName());
        return PostResponse.builder()
                .postId(postCreated.getPostId().toHexString())
                .personId(postCreated.getPersonId().toHexString())
                .personName(personCollection.getFullName())
                .nickName(personCollection.getNickname())
                .message(postCreated.getMessage())
                .datePublished(postCreated.getDatePublished())
                .image(postCreated.getImage())
                .likes(postCreated.getLikes().stream().map(ObjectId::toString).collect(Collectors.toList()))
                .comments(postCreated.getComments().stream().map(ObjectId::toString).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Page<PostResponse> getPosts(int page, int size) {
        Sort.Direction direction = Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "datePublished"));
        Page<PostCollection> posts = postRepository.findAll(pageable);
        List<PostResponse> listPosts = new ArrayList<>();
        for (int i = 0; i < posts.getContent().size(); i++) {
            PersonCollection personCollection = personRepository.findById(posts.getContent().get(i).getPersonId()).orElseThrow(() -> {
                return new NotFoundException("Person doest not exist");
            });

            PostResponse postResponse = PostResponse.builder()
                    .postId(posts.getContent().get(i).getPostId().toHexString())
                    .personId(posts.getContent().get(i).getPersonId().toHexString())
                    .personName(personCollection.getFullName())
                    .photoProfile(personCollection.getPhotoProfile())
                    .nickName(personCollection.getNickname())
                    .message(posts.getContent().get(i).getMessage())
                    .datePublished(posts.getContent().get(i).getDatePublished())
                    .image(posts.getContent().get(i).getImage())
                    .likes(posts.getContent().get(i).getLikes().stream().map(ObjectId::toString).collect(Collectors.toList()))
                    .comments(posts.getContent().get(i).getComments().stream().map(ObjectId::toString).collect(Collectors.toList()))
                    .build();
            listPosts.add(postResponse);
        }
        return new PageImpl<>(listPosts, pageable, posts.getTotalElements());
    }

    @Override
    public MessageResponse deletePost(String email, ObjectId postId) throws IOException {
        PersonCollection loggedPerson = this.getLoggedPerson(email);

        PostCollection postCollection = postRepository.findById(postId).orElseThrow(() -> {
            return new NotFoundException("Post not found");
        });

        if(!postCollection.getPersonId().equals(loggedPerson.getPersonId())) {
            return new MessageResponse("User don't have permission to delete this post");
        }

        List<ObjectId> commentsToDelete = postCollection.getComments();
        commentsToDelete.forEach(commentRepository::deleteById);

        if(postCollection.getImage() != null) {
            fireBaseStorageService.deleteFile(postCollection.getPostId().toHexString(), "posts/");
        }

        postRepository.delete(postCollection);
        return new MessageResponse("Post was deleted successfully");
    }

    @Override
    public MessageResponse giveLike(String email, ObjectId postId) {
        PersonCollection loggedPerson = this.getLoggedPerson(email);

        PostCollection postCollection = postRepository.findById(postId).orElseThrow(() -> {
            return new NotFoundException("Post not found");
        });

        postCollection.getLikes().add(loggedPerson.getPersonId());
        postRepository.save(postCollection);
        return new MessageResponse("Post with id "+postId+" was liked by "+loggedPerson.getPersonId());
    }

    @Override
    public MessageResponse removeLike(String email, ObjectId postId) {
        PostCollection postCollection = postRepository.findById(postId).orElseThrow(() -> {
            return new NotFoundException("Post not found");
        });

        PersonCollection loggedPerson = this.getLoggedPerson(email);

        postCollection.getLikes().remove(loggedPerson.getPersonId());
        postRepository.save(postCollection);
        return new MessageResponse("Post with id "+postId+" was disliked by "+loggedPerson.getPersonId());
    }

    @Override
    public List<LikesResponse> getLikes(ObjectId postId) {
        PostCollection postCollection = postRepository.findById(postId).orElseThrow(() -> {
            return new NotFoundException("Post not found");
        });

        List<String> listLikes = postCollection.getLikes().stream().map(ObjectId::toString).collect(Collectors.toList());
        List<LikesResponse> listLikesResponses = new ArrayList<>();
        for (String listLike : listLikes) {
            PersonCollection personCollection = personRepository.findById(new ObjectId(listLike)).orElseThrow(() -> {
                return new NotFoundException("Person not found");
            });
            LikesResponse likesResponse = LikesResponse.builder()
                    .personId(personCollection.getPersonId().toHexString())
                    .fullName(personCollection.getFullName())
                    .nickName(personCollection.getNickname())
                    .picturePhoto(personCollection.getPhotoProfile())
                    .build();
            listLikesResponses.add(likesResponse);
        }
        return listLikesResponses;
    }

    @Override
    public CommentsResponse makeComment(String email, ObjectId postId, String comment) {
        PersonCollection loggedPerson = this.getLoggedPerson(email);

        PostCollection postCollection = postRepository.findById(postId).orElseThrow(() -> {
            return new NotFoundException("Post not found");
        });

        CommentCollection commentCollection = CommentCollection.builder()
                .personId(loggedPerson.getPersonId())
                .comment(comment)
                .build();
        CommentCollection commentCreated = commentRepository.save(commentCollection);
        postCollection.getComments().add(commentCreated.getCommentId());
        postRepository.save(postCollection);
        return CommentsResponse.builder()
                .commentId(commentCreated.getCommentId().toHexString())
                .personId(commentCreated.getPersonId().toHexString())
                .picturePhoto(loggedPerson.getPhotoProfile())
                .fullName(loggedPerson.getFullName())
                .message(commentCreated.getComment())
                .build();
    }

    @Override
    public MessageResponse removeComment(String email, ObjectId commentId, ObjectId postId) {
        PersonCollection loggedPerson = this.getLoggedPerson(email);

        CommentCollection commentCollection = commentRepository.findById(commentId).orElseThrow(() -> {
            return new NotFoundException("Comment not found");
        });

        PostCollection postCollection = postRepository.findById(postId).orElseThrow(() -> {
            return new NotFoundException("Post not found");
        });

        if(!loggedPerson.getPersonId().equals(commentCollection.getPersonId())) {
            return new MessageResponse("User don't have permission to delete this comment");
        }

        postCollection.getComments().remove(commentId);
        postRepository.save(postCollection);
        commentRepository.deleteById(commentId);

        return new MessageResponse("Comment was deleted by "+loggedPerson.getFullName());
    }

    @Override
    public List<CommentsResponse> getComments(ObjectId postId) {
        PostCollection postCollection = postRepository.findById(postId).orElseThrow(() -> {
            return new NotFoundException("Post not found");
        });

        List<String> listComments = postCollection.getComments().stream().map(ObjectId::toString).collect(Collectors.toList());
        List<CommentsResponse> listCommentsResponses = new ArrayList<>();
        for (String listComment : listComments) {
            CommentCollection commentCollection = commentRepository.findById(new ObjectId(listComment)).orElseThrow(() -> {
                return new NotFoundException("Person not found");
            });

            PersonCollection personCollection = personRepository.findById(commentCollection.getPersonId()).orElseThrow(() -> {
                return new NotFoundException("Person not found");
            });

            CommentsResponse commentsResponse = CommentsResponse.builder()
                    .commentId(commentCollection.getCommentId().toHexString())
                    .personId(personCollection.getPersonId().toHexString())
                    .picturePhoto(personCollection.getPhotoProfile())
                    .fullName(personCollection.getFullName())
                    .message(commentCollection.getComment())
                    .build();
            listCommentsResponses.add(commentsResponse);
        }
        return listCommentsResponses;
    }

    public PersonCollection getLoggedPerson(String email) {
        UserCollection loggedUser = userRepository.findByEmail(email).orElseThrow(() -> {
            return new NotFoundException("User not found");
        });

        return personRepository.findByUserId(loggedUser.getIdUser()).orElseThrow(() -> {
            return new NotFoundException("Person not found");
        });
    }

}
