package com.mongodb.starter.repositories;

import com.mongodb.starter.models.PostLikeCommentEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostLikeCommentRepository extends MongoRepository<PostLikeCommentEntity, ObjectId> {
}
