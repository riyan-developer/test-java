package com.mongodb.starter.services;

import com.mongodb.starter.dtos.PostDTO;
import com.mongodb.starter.models.Post;
import com.mongodb.starter.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        return new PostDTO(postRepository.save(postDTO.toPostEntity()));
    }

    @Override
    public  List<Post> saveAll(List<PostDTO> postDTOs) {
//        return postRepository.saveAll(postDTOs);
    	return null;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public PostDTO findOne(String id) {
        return new PostDTO(postRepository.findOne(id));
    }

    @Override
    public long count() {
        return postRepository.count();
    }

    @Override
    public long delete(String id) {
        return postRepository.delete(id);
    }

    @Override
    public long deleteAll() {
        return postRepository.deleteAll();
    }

    @Override
    public PostDTO update(String id, PostDTO postDTO) {
        return new PostDTO(postRepository.update(postDTO.toPostEntity()));
    }

    @Override
    public long update(List<PostDTO> postDTOs) {
        return postRepository.update(postDTOs);
    }
}
