package com.zam.uanet.controllers;

import com.zam.uanet.payload.response.PersonSuggestionResponse;
import com.zam.uanet.payload.response.PostSuggestionResponse;
import com.zam.uanet.services.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(SuggestionController.API_PATH)
public class SuggestionController {

    public static final String API_PATH = "/api/suggestions";

    public final SuggestionService suggestionService;

    @Autowired
    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/users")
    public List<PersonSuggestionResponse> getSuggestionPersons() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return suggestionService.getPersonSuggestionList(email);
    }

    @GetMapping("/posts")
    public List<PostSuggestionResponse> getSuggestionPosts() {
        return suggestionService.getPostSuggestionList();
    }

}
