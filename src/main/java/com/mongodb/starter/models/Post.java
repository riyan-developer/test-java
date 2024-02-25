package com.mongodb.starter.models;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Post {

    private ObjectId id;
    private String name;
    private String description;

    public Post() {
    }

    public Post(ObjectId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ObjectId getId() {
        return id;
    }

    public Post setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Post setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Post setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(name, post.name) &&
                Objects.equals(description, post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
