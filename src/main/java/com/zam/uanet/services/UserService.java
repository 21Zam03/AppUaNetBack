package com.zam.uanet.services;

import com.zam.uanet.entities.UserEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {

    public UserEntity createUser(UserEntity user);
    public UserEntity getUser(ObjectId idUser);
    public List<UserEntity> listUser();
    public UserEntity updateUser(UserEntity user);
    public String deleteUser(ObjectId idUser);

}
