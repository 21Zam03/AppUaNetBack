package com.zam.uanet.controllers;

import com.zam.uanet.dtos.LoginResponse;
import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUser(@PathVariable(value = "id") ObjectId idUser) {
        return userService.getUser(idUser);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> listUser() {
        return userService.listUser();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserEntity updateUser(@RequestBody UserEntity userEntity) {
        return userService.updateUser(userEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable(value = "id") ObjectId idCliente) {
        return userService.deleteUser(idCliente);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> login(@RequestBody UserEntity userEntity) {
        return null;
    }

}
