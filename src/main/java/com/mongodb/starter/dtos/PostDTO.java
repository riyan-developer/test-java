package com.mongodb.starter.dtos;

import com.mongodb.starter.models.Post;
import org.bson.types.ObjectId;

public record PostDTO(
        String id,
        String name,
        String description) {

    public PostDTO(Post post) {
        this(post.getId() == null ? new ObjectId().toHexString() : post.getId().toHexString(), post.getName(),
             post.getDescription());
    }

    public Post toPostEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new Post(_id, name, description);
    }
    
}
