package com.zam.uanet.services.imp;

import com.zam.uanet.collections.PersonCollection;
import com.zam.uanet.collections.PostCollection;
import com.zam.uanet.collections.UserCollection;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.payload.response.PersonSuggestionResponse;
import com.zam.uanet.payload.response.PostSuggestionResponse;
import com.zam.uanet.repositories.PersonRepository;
import com.zam.uanet.repositories.PostRepository;
import com.zam.uanet.repositories.UserRepository;
import com.zam.uanet.services.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final PersonRepository personRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public SuggestionServiceImpl(PersonRepository personRepository, PostRepository postRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PersonSuggestionResponse> getPersonSuggestionList(String email) {
        PersonCollection loggedPerson = this.getLoggedPerson(email);
        List<PersonCollection> personCollectionList = personRepository.findRandomPersons(loggedPerson.getPersonId());
        List<PersonSuggestionResponse> personSuggestionResponseList = new ArrayList<>();
        for (PersonCollection personCollection : personCollectionList) {
            PersonSuggestionResponse personSuggestionResponse = PersonSuggestionResponse.builder()
                    .personId(personCollection.getPersonId().toHexString())
                    .userName(personCollection.getNickname())
                    .fullName(personCollection.getFullName())
                    .photo(personCollection.getPhotoProfile())
                    .build();
            personSuggestionResponseList.add(personSuggestionResponse);
        }
        return personSuggestionResponseList;
    }

    @Override
    public List<PostSuggestionResponse> getPostSuggestionList() {
        List<PostCollection> postCollectionList = postRepository.findRandomPosts();
        List<PostSuggestionResponse> postSuggestionResponseList = new ArrayList<>();
        for (PostCollection postCollection : postCollectionList) {
            PostSuggestionResponse postSuggestionResponse = PostSuggestionResponse.builder()
                    .postId(postCollection.getPostId().toHexString())
                    .message(postCollection.getMessage())
                    .authorName(personRepository.findById(postCollection.getPersonId()).orElseThrow(() -> {
                        return new NotFoundException("Person not found");
                    }).getFullName())
                    .likes(postCollection.getLikes().size())
                    .comments(postCollection.getComments().size())
                    .build();
            postSuggestionResponseList.add(postSuggestionResponse);
        }
        return postSuggestionResponseList;
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
