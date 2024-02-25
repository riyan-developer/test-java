package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Post;

import java.util.List;

public interface PostRepository {

    Post save(Post post);

    List<Post> saveAll(List<Post> posts);

    List<Post> findAll();

    Post findOne(String id);

    long count();

    long delete(String id);

    long deleteAll();

    Post update(Post post);

    long update(List<Post> posts);
}
