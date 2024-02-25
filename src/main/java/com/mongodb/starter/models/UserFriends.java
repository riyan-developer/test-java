package com.mongodb.starter.models;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

public class UserFriends {

    private ObjectId userId;
    private List<ObjectId> friendIds;

    public UserFriends() {
    }

    public UserFriends(ObjectId userId, List<ObjectId> friendIds) {
        this.userId = userId;
        this.friendIds = friendIds;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public UserFriends setUserId(ObjectId userId) {
        this.userId = userId;
        return this;
    }

    public List<ObjectId> getFriendIds() {
        return friendIds;
    }

    public UserFriends setFriendIds(List<ObjectId> friendIds) {
        this.friendIds = friendIds;
        return this;
    }

    @Override
    public String toString() {
        return "UserFriends{" +
                "userId=" + userId +
                ", friendIds=" + friendIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFriends that = (UserFriends) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(friendIds, that.friendIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendIds);
    }
}
