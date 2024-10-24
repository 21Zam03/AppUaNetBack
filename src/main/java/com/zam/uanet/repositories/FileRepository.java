package com.zam.uanet.repositories;

import com.zam.uanet.collections.FileCollection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<FileCollection, ObjectId> {
}
