package com.zam.uanet.services;

import com.zam.uanet.dtos.StudentFull;
import com.zam.uanet.dtos.SignUpDTO;
import com.zam.uanet.dtos.UserDto;
import com.zam.uanet.entities.UserEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {

    public UserDto createUser(UserEntity user);
    public UserDto getUser(ObjectId idUser);
    public List<UserDto> listUser();
    public UserDto updateUser(UserEntity user);
    public void deleteUser(ObjectId idUser);

    public StudentFull loginAction(String email, String password);
    public StudentFull signUpAction (SignUpDTO signUpDto);
}
