package com.mongodb.starter.services;

import com.mongodb.starter.dtos.CommentDTO;
import com.mongodb.starter.dtos.PostLikeCommentDTO;

import java.util.List;

public interface PostLikeCommentService {

    PostLikeCommentDTO save(PostLikeCommentDTO postLikeCommentDTO);

    List<PostLikeCommentDTO> findAll();

    PostLikeCommentDTO findOne(String postId);

    PostLikeCommentDTO addComment(String postId, CommentDTO commentDTO);
    
    boolean deleteLike(String postId, String likeId);

    boolean deleteComment(String postId, String commentId);

}
