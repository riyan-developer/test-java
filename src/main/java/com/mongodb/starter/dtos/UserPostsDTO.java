package com.mongodb.starter.dtos;

import com.mongodb.starter.models.UserPosts;
import org.bson.types.ObjectId;

import java.util.List;

public record UserPostsDTO(
        String userId,
        List<String> postIds) {

    public UserPostsDTO(UserPosts userPosts) {
        this(userPosts.getUserId() == null ? new ObjectId().toHexString() : userPosts.getUserId().toHexString(),
             mapPostIdsToStrings(userPosts.getPostIds()));
    }

    public UserPosts toUserPostsEntity() {
        ObjectId _userId = userId == null ? new ObjectId() : new ObjectId(userId);
        return new UserPosts(_userId, mapPostIdsToObjectId(postIds));
    }

    private static List<ObjectId> mapPostIdsToObjectId(List<String> postIds) {
        return postIds.stream().map(ObjectId::new).toList();
    }

    private static List<String> mapPostIdsToStrings(List<ObjectId> postIds) {
        return postIds.stream().map(ObjectId::toHexString).toList();
    }
}
