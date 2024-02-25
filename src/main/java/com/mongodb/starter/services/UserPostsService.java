package com.mongodb.starter.services;

import com.mongodb.starter.dtos.UserPostsDTO;

import java.util.List;

public interface UserPostsService {

    UserPostsDTO save(UserPostsDTO userPostsDTO);

    List<UserPostsDTO> saveAll(List<UserPostsDTO> userPostsDTOList);

    List<UserPostsDTO> findAll();

    UserPostsDTO findOne(String userId);

    long delete(String userId);

    long deleteAll();

    UserPostsDTO update(UserPostsDTO userPostsDTO);

    long update(List<UserPostsDTO> userPostsDTOList);
}
