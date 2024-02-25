package com.mongodb.starter.dtos;

import com.mongodb.starter.models.PostLikeCommentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PostLikeCommentDTO {

    private String postId;
    private List<String> likes;
    private List<CommentDTO> comments;

    public PostLikeCommentDTO() {
    }

    public PostLikeCommentDTO(String postId, List<String> likes, List<CommentDTO> comments) {
        this.postId = postId;
        this.likes = likes;
        this.comments = comments;
    }

    public PostLikeCommentDTO(PostLikeCommentEntity postLikeCommentEntity) {
        this.postId = postLikeCommentEntity.getPostId().toHexString();
        this.likes = postLikeCommentEntity.getLikes().stream().map(ObjectId::toHexString).collect(Collectors.toList());
        this.comments = postLikeCommentEntity.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public PostLikeCommentEntity toPostLikeCommentEntity() {
        return new PostLikeCommentEntity(ObjectId.get(), likes.stream().map(ObjectId::new).collect(Collectors.toList()),
                comments.stream().map(CommentDTO::toCommentEntity).collect(Collectors.toList()));
    }
}
