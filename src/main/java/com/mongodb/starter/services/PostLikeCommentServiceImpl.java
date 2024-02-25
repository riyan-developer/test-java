package com.mongodb.starter.services;

import com.mongodb.starter.dtos.CommentDTO;
import com.mongodb.starter.dtos.PostLikeCommentDTO;
import com.mongodb.starter.models.PostLikeCommentEntity;
import com.mongodb.starter.repositories.PostLikeCommentRepository;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostLikeCommentServiceImpl implements PostLikeCommentService {

    private final PostLikeCommentRepository postLikeCommentRepository;

    public PostLikeCommentServiceImpl(PostLikeCommentRepository postLikeCommentRepository) {
        this.postLikeCommentRepository = postLikeCommentRepository;
    }

    @Override
    public PostLikeCommentDTO save(PostLikeCommentDTO postLikeCommentDTO) {
        PostLikeCommentEntity postLikeCommentEntity = postLikeCommentDTO.toPostLikeCommentEntity();
        postLikeCommentEntity = postLikeCommentRepository.save(postLikeCommentEntity);
        return new PostLikeCommentDTO(postLikeCommentEntity);
    }

    @Override
    public List<PostLikeCommentDTO> findAll() {
        List<PostLikeCommentEntity> postLikeCommentEntities = postLikeCommentRepository.findAll();
        return postLikeCommentEntities.stream().map(PostLikeCommentDTO::new).collect(Collectors.toList());
    }

    @Override
    public PostLikeCommentDTO findOne(String postId) {
        return postLikeCommentRepository.findById(new ObjectId(postId)).map(PostLikeCommentDTO::new).orElse(null);
    }

    @Override
    public PostLikeCommentDTO addComment(String postId, CommentDTO commentDTO) {
        return postLikeCommentRepository.findById(new ObjectId(postId)).map(postLikeCommentEntity -> {
            postLikeCommentEntity.getComments().add(commentDTO.toCommentEntity());
            postLikeCommentEntity = postLikeCommentRepository.save(postLikeCommentEntity);
            return new PostLikeCommentDTO(postLikeCommentEntity);
        }).orElse(null);
    }
    
    @Override
    public boolean deleteLike(String postId, String likeId) {
        ObjectId postObjectId = new ObjectId(postId);
        ObjectId likeObjectId = new ObjectId(likeId);

        PostLikeCommentEntity postLikeCommentEntity = postLikeCommentRepository.findById(postObjectId).orElse(null);

        if (postLikeCommentEntity != null && postLikeCommentEntity.getLikes().remove(likeObjectId)) {
            postLikeCommentRepository.save(postLikeCommentEntity);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteComment(String postId, String commentId) {
        ObjectId postObjectId = new ObjectId(postId);
        ObjectId commentObjectId = new ObjectId(commentId);

        PostLikeCommentEntity postLikeCommentEntity = postLikeCommentRepository.findById(postObjectId).orElse(null);

        if (postLikeCommentEntity != null) {
            boolean removed = postLikeCommentEntity.getComments().removeIf(comment -> comment.getUserId().equals(commentObjectId));
            if (removed) {
                postLikeCommentRepository.save(postLikeCommentEntity);
                return true;
            }
        }

        return false;
    }

}
