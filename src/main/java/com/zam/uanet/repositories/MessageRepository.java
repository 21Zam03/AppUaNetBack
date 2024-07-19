package com.zam.uanet.repositories;

import com.zam.uanet.entities.MessageEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<MessageEntity, ObjectId> {

    List<MessageEntity> findByIdChat(ObjectId idChat);

}
