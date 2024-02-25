package com.mongodb.starter.repositories;

import com.mongodb.starter.models.UserPosts;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserPostsRepository {

    UserPosts save(UserPosts userPosts);

    List<UserPosts> saveAll(List<UserPosts> userPostsList);

    List<UserPosts> findAll();

    UserPosts findOne(String userId);

    long delete(String userId);

    long deleteAll();

    UserPosts update(UserPosts userPosts);

    long update(List<UserPosts> userPostsList);
}
