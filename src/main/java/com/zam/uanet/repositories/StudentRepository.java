package com.zam.uanet.repositories;

import com.zam.uanet.entities.StudentEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<StudentEntity, ObjectId> {

    @Query("{'idUser' : ?0}")
    public StudentEntity findByUserQuery(ObjectId idUser);

}
