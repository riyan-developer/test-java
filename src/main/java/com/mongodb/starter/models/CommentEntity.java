package com.mongodb.starter.models;

import org.bson.types.ObjectId;

public class CommentEntity {

    private ObjectId userId;
    private String text;

    public CommentEntity() {
    }

    public CommentEntity(ObjectId userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
