package com.example.hearurbackend.service;

import com.example.hearurbackend.entity.PostEntity;
import com.example.hearurbackend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
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
}