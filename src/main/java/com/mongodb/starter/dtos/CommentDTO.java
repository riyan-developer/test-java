package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;

import com.mongodb.starter.models.CommentEntity;

public class CommentDTO {

    private String userId;
    private String text;

    public CommentDTO() {
    }

    public CommentDTO(String userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public CommentDTO(CommentEntity commentEntity) {
        this.userId = commentEntity.getUserId().toHexString();
        this.text = commentEntity.getText();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentEntity toCommentEntity() {
        return new CommentEntity(new ObjectId(userId), text);
    }
}
