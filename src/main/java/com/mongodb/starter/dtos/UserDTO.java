package com.mongodb.starter.dtos;

import com.mongodb.starter.models.User;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public record UserDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        String password,
        Date createdAt) {

    public UserDTO(User user) {
        this(user.getId() == null ? new ObjectId().toHexString() : user.getId().toHexString(), user.getFirstName(),
             user.getLastName(), user.getEmail(), user.getPassword(), user.getCreatedAt());
    }

    public User toUserEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new User(_id, firstName, lastName, email, password, createdAt);
    }
}
