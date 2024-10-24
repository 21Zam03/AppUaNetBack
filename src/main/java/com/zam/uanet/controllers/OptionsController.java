package com.zam.uanet.controllers;

import com.zam.uanet.payload.response.RoleResponse;
import com.zam.uanet.services.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(OptionsController.API_PATH)
public class OptionsController {

    public static final String API_PATH = "/api/options";

    private final OptionsService optionsService;

    @Autowired
    public OptionsController(OptionsService optionsService) {
        this.optionsService = optionsService;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoles() {
        return new ResponseEntity<>(optionsService.getRolesList(), HttpStatus.OK);
    }


}
