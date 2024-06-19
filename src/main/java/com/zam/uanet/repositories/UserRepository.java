package com.zam.uanet.repositories;

import com.zam.uanet.entities.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    public UserEntity findByEmailAndPassword(String email, String password);

}
