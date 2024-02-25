package com.mongodb.starter.services;

import com.mongodb.starter.dtos.UserFriendsDTO;
import com.mongodb.starter.models.UserFriends;
import com.mongodb.starter.repositories.UserFriendsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFriendsServiceImpl implements UserFriendsService {

    private final UserFriendsRepository userFriendsRepository;

    public UserFriendsServiceImpl(UserFriendsRepository userFriendsRepository) {
        this.userFriendsRepository = userFriendsRepository;
    }

    @Override
    public UserFriendsDTO save(UserFriendsDTO userFriendsDTO) {
        UserFriends userFriends = userFriendsRepository.save(userFriendsDTO.toUserFriendsEntity());
        return new UserFriendsDTO(userFriends);
    }

    @Override
    public List<UserFriendsDTO> saveAll(List<UserFriendsDTO> userFriendsDTOList) {
        List<UserFriends> userFriendsList = userFriendsRepository.saveAll(
                userFriendsDTOList.stream().map(UserFriendsDTO::toUserFriendsEntity).collect(Collectors.toList()));
        return userFriendsList.stream().map(UserFriendsDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<UserFriendsDTO> findAll() {
        List<UserFriends> userFriendsList = userFriendsRepository.findAll();
        return userFriendsList.stream().map(UserFriendsDTO::new).collect(Collectors.toList());
    }

    @Override
    public UserFriendsDTO findOne(String userId) {
        UserFriends userFriends = userFriendsRepository.findOne(userId);
        return userFriends != null ? new UserFriendsDTO(userFriends) : null;
    }

    @Override
    public long delete(String userId) {
        return userFriendsRepository.delete(userId);
    }

    @Override
    public long deleteAll() {
        return userFriendsRepository.deleteAll();
    }

    @Override
    public UserFriendsDTO update(UserFriendsDTO userFriendsDTO) {
        UserFriends userFriends = userFriendsRepository.update(userFriendsDTO.toUserFriendsEntity());
        return new UserFriendsDTO(userFriends);
    }

    @Override
    public long update(List<UserFriendsDTO> userFriendsDTOList) {
        List<UserFriends> userFriendsList = userFriendsDTOList.stream().map(UserFriendsDTO::toUserFriendsEntity)
                                                             .collect(Collectors.toList());
        return userFriendsRepository.update(userFriendsList);
    }
}
