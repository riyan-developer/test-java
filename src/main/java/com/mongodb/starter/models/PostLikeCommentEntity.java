package com.mongodb.starter.models;

import org.bson.types.ObjectId;

import java.util.List;

public class PostLikeCommentEntity {

    private ObjectId postId;
    private List<ObjectId> likes;
    private List<CommentEntity> comments;

    public PostLikeCommentEntity() {
    }

    public PostLikeCommentEntity(ObjectId postId, List<ObjectId> likes, List<CommentEntity> comments) {
        this.postId = postId;
        this.likes = likes;
        this.comments = comments;
    }

    public ObjectId getPostId() {
        return postId;
    }

    public void setPostId(ObjectId postId) {
        this.postId = postId;
    }

    public List<ObjectId> getLikes() {
        return likes;
    }

    public void setLikes(List<ObjectId> likes) {
        this.likes = likes;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}
