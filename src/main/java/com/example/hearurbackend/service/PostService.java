package com.example.hearurbackend.service;

import com.example.hearurbackend.dto.post.PostDto;
import com.example.hearurbackend.entity.Post;
import com.example.hearurbackend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(
            PostRepository postRepository,
            UserService userService
    ) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<PostDto> getPostList() {
        List<Post> postEntities = postRepository.findAll();
        return postEntities.stream()
                .map(post -> new PostDto(post.getNo(), post.getCategory(), post.getTitle(), userService.getUser(post.getAuthor()).getNickname() , post.getCreateDate()))
                .collect(Collectors.toList());
    }

    public Post getPost(Long postNo) {
        return postRepository.findById(postNo).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postNo));
    }

    public Post createPost(PostDto postDTO, String username) {
        LocalDateTime now = LocalDateTime.now();
        Post post = Post.builder()
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

    public void updatePost(Long postNo, PostDto postDTO, String username) {
        Post post = postRepository.findById(postNo).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + postNo));
        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You are not the author of this post.");
        }
        post.updatePost(postDTO.getTitle(), postDTO.getContent());
        postRepository.save(post);
    }

    public void deletePost(Long postNo, String username) {
        Post post = postRepository.findById(postNo).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + postNo));
        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You are not the author of this post.");
        }
        postRepository.deleteById(postNo);
    }
}