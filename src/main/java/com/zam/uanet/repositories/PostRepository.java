package com.zam.uanet.repositories;

import com.zam.uanet.dtos.PostDTO;
import com.zam.uanet.entities.PostEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, ObjectId> {
    @Query("{'idStudent' : ?0}")
    public List<PostEntity> findByStudentQuery(ObjectId idStudent);
}
