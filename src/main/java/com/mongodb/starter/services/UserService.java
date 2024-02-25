package com.mongodb.starter.services;

import com.mongodb.starter.dtos.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO save(UserDTO userDTO);

    List<UserDTO> saveAll(List<UserDTO> users);

    List<UserDTO> findAll();

    List<UserDTO> findAll(List<String> ids);

    UserDTO findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    UserDTO update(UserDTO userDTO);

    long update(List<UserDTO> users);


}
