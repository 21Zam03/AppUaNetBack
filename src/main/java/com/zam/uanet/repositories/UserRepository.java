package com.zam.uanet.repositories;

import com.zam.uanet.collections.UserCollection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserCollection, ObjectId> {

    public UserCollection findByEmail(String email, String password);
    public Optional<UserCollection> findByEmail(String email);
    boolean existsByEmail(String email);

}
