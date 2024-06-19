package com.zam.uanet.repositories;

import com.zam.uanet.entities.FriendEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends MongoRepository<FriendEntity, ObjectId> {

    @Query("{ 'userId1': ?0, 'userId2': ?1 }")
    FriendEntity findFriendsByUserId1AndUserId2(ObjectId userId1, ObjectId userId2);

}
