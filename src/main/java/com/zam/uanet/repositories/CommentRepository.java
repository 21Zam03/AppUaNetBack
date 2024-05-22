package com.zam.uanet.repositories;

import com.zam.uanet.entities.CommentEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, ObjectId> {
}
