package com.mongodb.starter.services;

import com.mongodb.starter.dtos.PostDTO;
import com.mongodb.starter.models.Post;

import java.util.List;

public interface PostService {

    PostDTO save(PostDTO postDTO);

    List<Post> saveAll(List<PostDTO> postDTOs);

    List<Post> findAll();

    PostDTO findOne(String id);

    long count();

    long delete(String id);

    long deleteAll();

    PostDTO update(String id, PostDTO postDTO);

    long update(List<PostDTO> postDTOs);
}
