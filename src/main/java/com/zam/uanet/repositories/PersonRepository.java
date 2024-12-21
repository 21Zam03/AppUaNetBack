package com.zam.uanet.repositories;

import com.zam.uanet.collections.PersonCollection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<PersonCollection, ObjectId> {

    @Query("{ 'userId': ?0}")
    public Optional<PersonCollection> findByUserId(ObjectId userId);

    @Aggregation(pipeline = {
            "{ $match: { '_id': { $ne: ?0 } } }",  // Excluye el documento con el ID especificado
            "{ $sample: { size: 3 } }"  // Obtiene 3 documentos aleatorios
    })
    List<PersonCollection> findRandomPersons(ObjectId personId);
}
