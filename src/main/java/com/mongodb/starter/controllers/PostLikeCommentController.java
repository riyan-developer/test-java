package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.CommentDTO;
import com.mongodb.starter.dtos.PostLikeCommentDTO;
import com.mongodb.starter.services.PostLikeCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-like-comment")
public class PostLikeCommentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PostLikeCommentController.class);
    private final PostLikeCommentService postLikeCommentService;

    public PostLikeCommentController(PostLikeCommentService postLikeCommentService) {
        this.postLikeCommentService = postLikeCommentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostLikeCommentDTO savePostLikeComment(@RequestBody PostLikeCommentDTO postLikeCommentDTO) {
        return postLikeCommentService.save(postLikeCommentDTO);
    }

    @GetMapping
    public List<PostLikeCommentDTO> getAllPostLikeComments() {
        return postLikeCommentService.findAll();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostLikeCommentDTO> getPostLikeComment(@PathVariable String postId) {
        PostLikeCommentDTO postLikeCommentDTO = postLikeCommentService.findOne(postId);
        if (postLikeCommentDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(postLikeCommentDTO);
    }

    @PostMapping("/{postId}/add-comment")
    public ResponseEntity<PostLikeCommentDTO> addComment(@PathVariable String postId, @RequestBody CommentDTO commentDTO) {
        PostLikeCommentDTO postLikeCommentDTO = postLikeCommentService.addComment(postId, commentDTO);
        if (postLikeCommentDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(postLikeCommentDTO);
    }
    
 // New methods for deletion
    @DeleteMapping("/{postId}/delete-like/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable String postId, @PathVariable String likeId) {
        if (postLikeCommentService.deleteLike(postId, likeId)) {
            return ResponseEntity.ok("Like deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Like not found");
        }
    }

    @DeleteMapping("/{postId}/delete-comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String postId, @PathVariable String commentId) {
        if (postLikeCommentService.deleteComment(postId, commentId)) {
            return ResponseEntity.ok("Comment deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
