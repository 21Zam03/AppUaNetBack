package com.zam.uanet.repositories;

import com.zam.uanet.collections.PostCollection;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<PostCollection, ObjectId> {

    @Query("{'idStudent' : ?0}")
    public List<PostCollection> findByStudentQuery(ObjectId idStudent);

    Page<PostCollection> findByPersonId(ObjectId idStudent, Pageable pageable);

    Page<PostCollection> findAllByOrderByDatePublishedDesc(Pageable pageable);

    public boolean existsByPostId(ObjectId postId);

}
