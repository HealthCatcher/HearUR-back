package com.example.hearurbackend.service;

import com.example.hearurbackend.dto.PostDTO;
import com.example.hearurbackend.entity.PostEntity;
import com.example.hearurbackend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDTO> getPostList() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream()
                .map(post -> new PostDTO(post.getId(), post.getCategory(), post.getTitle(), post.getAuthor(), post.getCreateDate()))
                .collect(Collectors.toList());
    }

    public PostEntity getPost(UUID postId) {
        return postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
    }

    public PostEntity createPost(PostDTO postDTO, String username) {
        LocalDateTime now = LocalDateTime.now();
        PostEntity post = PostEntity.builder()
                .title(postDTO.getTitle())
                .category(postDTO.getCategory())
                .content(postDTO.getContent())
                .author(username)
                .createDate(now)
                .updateDate(now)
                .isUpdated(false)
                .build();
        return postRepository.save(post);
    }

    public void updatePost(UUID postId, PostDTO postDTO, String username) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + postId));
        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You are not the author of this post.");
        }
        post.updatePost(postDTO.getTitle(), postDTO.getContent());
        postRepository.save(post);
    }

    public void deletePost(UUID postId, String username) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + postId));
        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You are not the author of this post.");
        }
        postRepository.deleteById(postId);
    }
}