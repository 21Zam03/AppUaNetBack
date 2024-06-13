package com.zam.uanet.repositories;

import com.zam.uanet.entities.CommentEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, ObjectId> {
    @Query("{'idPost' : ?0}")
    public List<CommentEntity> findByPostQuery(ObjectId idPost);
}
