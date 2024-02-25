package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.UserDTO;
import com.mongodb.starter.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO postUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDTO> postUsers(@RequestBody List<UserDTO> users) {
        return userService.saveAll(users);
    }

    @GetMapping("users")
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        UserDTO userDTO = userService.findOne(id);
        if (userDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("users/{ids}")
    public List<UserDTO> getUsers(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return userService.findAll(listIds);
    }

    @GetMapping("users/count")
    public Long getCount() {
        return userService.count();
    }

    @DeleteMapping("user/{id}")
    public Long deleteUser(@PathVariable String id) {
        return userService.delete(id);
    }

    @DeleteMapping("users/{ids}")
    public Long deleteUsers(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return userService.delete(listIds);
    }

    @DeleteMapping("users")
    public Long deleteUsers() {
        return userService.deleteAll();
    }

    @PutMapping("user")
    public UserDTO putUser(@RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @PutMapping("users")
    public Long putUsers(@RequestBody List<UserDTO> users) {
        return userService.update(users);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
