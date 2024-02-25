package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.UserFriendsDTO;
import com.mongodb.starter.services.UserFriendsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-friends")
public class UserFriendsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserFriendsController.class);
    private final UserFriendsService userFriendsService;

    public UserFriendsController(UserFriendsService userFriendsService) {
        this.userFriendsService = userFriendsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserFriendsDTO saveUserFriends(@RequestBody UserFriendsDTO userFriendsDTO) {
        return userFriendsService.save(userFriendsDTO);
    }

    @GetMapping
    public List<UserFriendsDTO> getAllUserFriends() {
        return userFriendsService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserFriendsDTO> getUserFriends(@PathVariable String userId) {
        UserFriendsDTO userFriendsDTO = userFriendsService.findOne(userId);
        if (userFriendsDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userFriendsDTO);
    }

    @PutMapping("/{userId}")
    public UserFriendsDTO updateUserFriends(@PathVariable String userId, @RequestBody UserFriendsDTO userFriendsDTO) {
//        userFriendsDTO.setUserId(userId);
        return userFriendsService.update(userFriendsDTO);
    }

    @DeleteMapping("/{userId}")
    public Long deleteUserFriends(@PathVariable String userId) {
        return userFriendsService.delete(userId);
    }

    @DeleteMapping
    public Long deleteAllUserFriends() {
        return userFriendsService.deleteAll();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
