package com.mongodb.starter.services;

import com.mongodb.starter.dtos.UserFriendsDTO;

import java.util.List;

public interface UserFriendsService {

    UserFriendsDTO save(UserFriendsDTO userFriendsDTO);

    List<UserFriendsDTO> saveAll(List<UserFriendsDTO> userFriendsDTOList);

    List<UserFriendsDTO> findAll();

    UserFriendsDTO findOne(String userId);

    long delete(String userId);

    long deleteAll();

    UserFriendsDTO update(UserFriendsDTO userFriendsDTO);

    long update(List<UserFriendsDTO> userFriendsDTOList);
}
