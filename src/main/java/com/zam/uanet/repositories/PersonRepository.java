package com.zam.uanet.repositories;

import com.zam.uanet.collections.PersonCollection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<PersonCollection, ObjectId> {

    public Optional<PersonCollection> findByUserId(ObjectId idUser);


}
