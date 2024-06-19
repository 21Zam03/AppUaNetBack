package com.zam.uanet.controllers;

import com.zam.uanet.dtos.LoginParameter;
import com.zam.uanet.dtos.SignUpDTO;
import com.zam.uanet.dtos.StudentFull;
import com.zam.uanet.dtos.UserDto;
import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable(value = "id") ObjectId idUser) {
        return userService.getUser(idUser);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> listUser() {
        return userService.listUser();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserEntity userEntity) {
        return userService.updateUser(userEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable(value = "id") ObjectId idCliente) {
        userService.deleteUser(idCliente);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentFull> login(@RequestBody LoginParameter loginParameter) {
        return ResponseEntity.ok(userService.loginAction(loginParameter.getEmail(), loginParameter.getPassword()));
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentFull> signUp(@RequestBody SignUpDTO signUpDto) {
        signUpDto.getStudent().setIntereses(new ArrayList<>());
        signUpDto.getStudent().setHobbies(new ArrayList<>());
        return ResponseEntity.ok(userService.signUpAction(signUpDto));
    }

}
