package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.PostDTO;
import com.mongodb.starter.models.Post;
import com.mongodb.starter.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.save(postDTO);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Post> createPosts(@RequestBody List<PostDTO> postDTOs) {
        return postService.saveAll(postDTOs);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable String id) {
        PostDTO postDTO = postService.findOne(id);
        if (postDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/count")
    public Long getPostCount() {
        return postService.count();
    }

    @DeleteMapping("/{id}")
    public Long deletePost(@PathVariable String id) {
        return postService.delete(id);
    }

    @DeleteMapping
    public Long deleteAllPosts() {
        return postService.deleteAll();
    }

    @PutMapping("/{id}")
    public PostDTO updatePost(@PathVariable String id, @RequestBody PostDTO postDTO) {
//        postDTO.setId(id);
        return postService.update(id, postDTO);
    }

    @PutMapping("/batch")
    public Long updatePosts(@RequestBody List<PostDTO> postDTOs) {
        return postService.update(postDTOs);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
