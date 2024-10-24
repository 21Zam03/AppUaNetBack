package com.zam.uanet.controllers;

import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.payload.response.PersonStartResponse;
import com.zam.uanet.services.AccountService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AccountController.API_PATH)
public class AccountController {

    public static final String API_PATH = "/api/accounts";

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService studentService) {
        this.accountService = studentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PersonStartResponse> getPerson(@RequestParam String personId) {
        return new ResponseEntity<>(accountService.getPerson(new ObjectId(personId)), HttpStatus.OK);
    }

    @PutMapping(value ="/profile_photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> changePhotoProfile(
            @RequestPart(value = "file") MultipartFile file) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<>(accountService.changePhotoProfile(email, file),  HttpStatus.OK);
    }

}
