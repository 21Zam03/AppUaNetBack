package com.zam.uanet.services;

import com.zam.uanet.payload.response.PersonSuggestionResponse;
import com.zam.uanet.payload.response.PostSuggestionResponse;

import java.util.List;

public interface SuggestionService {

    public List<PersonSuggestionResponse> getPersonSuggestionList(String email);
    public List<PostSuggestionResponse> getPostSuggestionList();

}
