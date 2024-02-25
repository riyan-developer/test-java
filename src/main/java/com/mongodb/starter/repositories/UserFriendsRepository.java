package com.mongodb.starter.repositories;

import com.mongodb.starter.models.UserFriends;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserFriendsRepository {

    UserFriends save(UserFriends userFriends);

    List<UserFriends> saveAll(List<UserFriends> userFriendsList);

    List<UserFriends> findAll();

    UserFriends findOne(ObjectId userId);

    long delete(ObjectId userId);

    long deleteAll();

    UserFriends update(UserFriends userFriends);

    long update(List<UserFriends> userFriendsList);
}
