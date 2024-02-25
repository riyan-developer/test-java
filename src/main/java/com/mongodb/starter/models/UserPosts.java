package com.mongodb.starter.models;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

public class UserPosts {

    private ObjectId userId;
    private List<ObjectId> postIds;

    public UserPosts() {
    }

    public UserPosts(ObjectId userId, List<ObjectId> postIds) {
        this.userId = userId;
        this.postIds = postIds;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public UserPosts setUserId(ObjectId userId) {
        this.userId = userId;
        return this;
    }

    public List<ObjectId> getPostIds() {
        return postIds;
    }

    public UserPosts setPostIds(List<ObjectId> postIds) {
        this.postIds = postIds;
        return this;
    }

    @Override
    public String toString() {
        return "UserPosts{" +
                "userId=" + userId +
                ", postIds=" + postIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPosts userPosts = (UserPosts) o;
        return Objects.equals(userId, userPosts.userId) &&
                Objects.equals(postIds, userPosts.postIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postIds);
    }
}
