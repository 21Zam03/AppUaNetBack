package com.zam.uanet.repositories;

import com.zam.uanet.collections.RoleCollection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<RoleCollection, ObjectId> {

    public boolean existsByRoleName(String rolName);

}
