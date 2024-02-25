package com.mongodb.starter.services;

import com.mongodb.starter.dtos.UserPostsDTO;
import com.mongodb.starter.models.UserPosts;
import com.mongodb.starter.repositories.UserPostsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPostsServiceImpl implements UserPostsService {

    private final UserPostsRepository userPostsRepository;

    public UserPostsServiceImpl(UserPostsRepository userPostsRepository) {
        this.userPostsRepository = userPostsRepository;
    }

    @Override
    public UserPostsDTO save(UserPostsDTO userPostsDTO) {
        UserPosts userPosts = userPostsRepository.save(userPostsDTO.toUserPostsEntity());
        return new UserPostsDTO(userPosts);
    }

    @Override
    public List<UserPostsDTO> saveAll(List<UserPostsDTO> userPostsDTOList) {
        List<UserPosts> userPostsList = userPostsRepository.saveAll(
                userPostsDTOList.stream().map(UserPostsDTO::toUserPostsEntity).collect(Collectors.toList()));
        return userPostsList.stream().map(UserPostsDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<UserPostsDTO> findAll() {
        List<UserPosts> userPostsList = userPostsRepository.findAll();
        return userPostsList.stream().map(UserPostsDTO::new).collect(Collectors.toList());
    }

    @Override
    public UserPostsDTO findOne(String userId) {
        UserPosts userPosts = userPostsRepository.findOne(userId);
        return userPosts != null ? new UserPostsDTO(userPosts) : null;
    }

    @Override
    public long delete(String userId) {
        return userPostsRepository.delete(userId);
    }

    @Override
    public long deleteAll() {
        return userPostsRepository.deleteAll();
    }

    @Override
    public UserPostsDTO update(UserPostsDTO userPostsDTO) {
        UserPosts userPosts = userPostsRepository.update(userPostsDTO.toUserPostsEntity());
        return new UserPostsDTO(userPosts);
    }

    @Override
    public long update(List<UserPostsDTO> userPostsDTOList) {
        List<UserPosts> userPostsList = userPostsDTOList.stream().map(UserPostsDTO::toUserPostsEntity)
                                                       .collect(Collectors.toList());
        return userPostsRepository.update(userPostsList);
    }
}
