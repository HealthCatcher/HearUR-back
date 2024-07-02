package com.example.hearurbackend.service;

import com.example.hearurbackend.entity.PostEntity;
import com.example.hearurbackend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {
    private final PostRepository postRepository;
    public CommunityService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public List<PostEntity> getPostList() {
        return postRepository.findAll();
    }
}
