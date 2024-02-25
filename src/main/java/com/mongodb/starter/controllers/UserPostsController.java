package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.PostDTO;
import com.mongodb.starter.dtos.UserDTO;
import com.mongodb.starter.dtos.UserPostsDTO;
import com.mongodb.starter.services.UserPostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-posts")
public class UserPostsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserPostsController.class);
    private final UserPostsService userPostsService;

    public UserPostsController(UserPostsService userPostsService) {
        this.userPostsService = userPostsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserPostsDTO addPostToUser(@RequestBody UserPostsDTO userPostsDTO) {
        return userPostsService.save(userPostsDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable String userId) {
        UserPostsDTO userPostsDTO = userPostsService.findOne(userId);
        if (userPostsDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userPostsDTO.getPostIds().stream().map(PostDTO::new).toList());
    }

    @PutMapping("/{userId}")
    public UserPostsDTO editPostOfUser(@PathVariable String userId, @RequestBody UserPostsDTO userPostsDTO) {
//        userPostsDTO.setUserId(userId);
        return userPostsService.update(userPostsDTO);
    }

    @DeleteMapping("/{userId}")
    public Long deletePostsOfUser(@PathVariable String userId) {
        return userPostsService.delete(userId);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
