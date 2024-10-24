package com.zam.uanet.repositories;

import com.zam.uanet.collections.PermissionCollection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends MongoRepository<PermissionCollection, ObjectId> {
}
