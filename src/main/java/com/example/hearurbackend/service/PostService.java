package com.example.hearurbackend.service;

import com.example.hearurbackend.dto.PostDTO;
import com.example.hearurbackend.entity.PostEntity;
import com.example.hearurbackend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostEntity> getPostList() {
        return postRepository.findAll();
    }

    public PostEntity getPost(UUID postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public PostEntity createPost(PostDTO postDTO, String username) {
        log.info("서비스 도착 username: {}", username);
        LocalDateTime now = LocalDateTime.now();
        PostEntity post = PostEntity.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(username)
                .createDate(now)
                .updateDate(now)
                .isUpdated(false)
                .build();
        return postRepository.save(post);
    }

    public PostEntity updatePost(UUID postId, PostDTO postDTO) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + postId));
        post.updatePost(postDTO.getTitle(), postDTO.getContent());
        return postRepository.save(post);
    }

    public void isAuthor(UUID postId, String username) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + postId));
        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You are not the author of this post.");
        }
    }
}