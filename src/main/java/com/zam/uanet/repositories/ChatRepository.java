package com.zam.uanet.repositories;

import com.zam.uanet.dtos.ChatDTO;
import com.zam.uanet.entities.ChatEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<ChatEntity, ObjectId> {

    @Query("{ 'students': ?0 }")
    List<ChatEntity> findByStudentIdOrderByCreatedAtDesc(ObjectId studentId);

}
