package com.mongodb.starter.services;

import com.mongodb.starter.dtos.UserDTO;
import com.mongodb.starter.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return new UserDTO(userRepository.save(userDTO.toUserEntity()));
    }

    @Override
    public List<UserDTO> saveAll(List<UserDTO> users) {
        return users.stream()
                    .map(UserDTO::toUserEntity)
                    .peek(userRepository::save)
                    .map(UserDTO::new)
                    .toList();
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    @Override
    public List<UserDTO> findAll(List<String> ids) {
        return userRepository.findAll(ids).stream().map(UserDTO::new).toList();
    }

    @Override
    public UserDTO findOne(String id) {
        return new UserDTO(userRepository.findOne(id));
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public long delete(String id) {
        return userRepository.delete(id);
    }

    @Override
    public long delete(List<String> ids) {
        return userRepository.delete(ids);
    }

    @Override
    public long deleteAll() {
        return userRepository.deleteAll();
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return new UserDTO(userRepository.update(userDTO.toUserEntity()));
    }

    @Override
    public long update(List<UserDTO> users) {
        return userRepository.update(users.stream().map(UserDTO::toUserEntity).toList());
    }

    // Additional methods specific to User entity, if needed

}
