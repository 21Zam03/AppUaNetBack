package com.zam.uanet.repositories;

import com.zam.uanet.entities.PostEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, ObjectId> {
}
