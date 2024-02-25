package com.mongodb.starter.dtos;

import com.mongodb.starter.models.UserFriends;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class UserFriendsDTO {

    private ObjectId userId;
    private List<ObjectId> friendIds;

    public UserFriendsDTO() {
    }

    public UserFriendsDTO(ObjectId userId, List<ObjectId> friendIds) {
        this.userId = userId;
        this.friendIds = friendIds;
    }

    public UserFriendsDTO(UserFriends userFriends) {
        this.userId = userFriends.getUserId();
        this.friendIds = userFriends.getFriendIds();
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public List<ObjectId> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<ObjectId> friendIds) {
        this.friendIds = friendIds;
    }

    public UserFriends toUserFriendsEntity() {
        return new UserFriends(userId, friendIds);
    }

    @Override
    public String toString() {
        return "UserFriendsDTO{" +
                "userId=" + userId +
                ", friendIds=" + friendIds +
                '}';
    }
}
