package com.example.hearurbackend.service;

import com.example.hearurbackend.dto.comment.CommentDto;
import com.example.hearurbackend.dto.post.PostRequestDto;
import com.example.hearurbackend.dto.post.PostResponseDto;
import com.example.hearurbackend.entity.Post;
import com.example.hearurbackend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public List<PostResponseDto> getPostList() {
        List<Post> postEntities = postRepository.findAll();
        return postEntities.stream()
                .map(post ->
                        PostResponseDto.builder()
                                .no(post.getNo())
                                .category(post.getCategory())
                                .title(post.getTitle())
                                .author(userService.getUser(post.getAuthor()).getNickname())
                                .createDate(post.getCreateDate())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public PostResponseDto getPostDetail(Long postNo) {
        Post post = postRepository.findById(postNo).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postNo));
        List<CommentDto> commentDTOList = post.getComments().stream()
                .map(comment -> new CommentDto(comment.getId(),
                        userService.getUser(comment.getAuthor()).getNickname(),
                        comment.getContent(),
                        comment.getCreateDate(),
                        comment.isUpdated()))
                .toList();

        return PostResponseDto.builder()
                .no(post.getNo())
                .category(post.getCategory())
                .title(post.getTitle())
                .content(post.getContent())
                .author(userService.getUser(post.getAuthor()).getNickname())
                .createDate(post.getCreateDate())
                .updateDate(post.getUpdateDate())
                .isUpdated(post.isUpdated())
                .comments(commentDTOList)
                .build();

    }

    @Transactional
    public Post createPost(PostRequestDto postDTO, String username) {
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

    @Transactional
    public void updatePost(Long postNo, PostRequestDto postDTO, String username) {
        Post post = postRepository.findById(postNo).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + postNo));
        if (!post.getAuthor().equals(username)) {
            throw new SecurityException("You are not the author of this post.");
        }
        post.updatePost(postDTO.getTitle(), postDTO.getContent());
        postRepository.save(post);
    }

    public void deletePost(Long postNo, String username) {
        Post post = postRepository.findById(postNo).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + postNo));
        if (!post.getAuthor().equals(username)) {
            throw new SecurityException("You are not the author of this post.");
        }
        postRepository.deleteById(postNo);
    }
}