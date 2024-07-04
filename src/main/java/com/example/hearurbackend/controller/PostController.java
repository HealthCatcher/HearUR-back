package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.CustomOAuth2User;
import com.example.hearurbackend.dto.PostDTO;
import com.example.hearurbackend.dto.PostResponse;
import com.example.hearurbackend.entity.PostEntity;
import com.example.hearurbackend.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/community")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService communityService) {
        this.postService = communityService;
    }

    @Operation(summary = "게시글 목록 조회")
    @GetMapping("/post")
    public ResponseEntity<String> getPostList() {
        List<PostEntity> postList = postService.getPostList();
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/post/{postId}")
    public ResponseEntity<String> getPostDetail(
            @PathVariable UUID postId
    ) {
        PostEntity post = postService.getPost(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "게시글 작성")
    @PostMapping("/post")
    public ResponseEntity<PostResponse> createPost(
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody PostDTO postDTO
    ) {
        log.info("auth: {}", auth.getUsername());
        PostEntity newPost = postService.createPost(postDTO, auth.getUsername());
        log.info("서비스 리턴 완료 newPost: {}", newPost.getId().toString());
        PostResponse responseDTO = PostResponse.builder()
                .id(newPost.getId())
                .title(newPost.getTitle())
                .content(newPost.getContent())
                .author(newPost.getAuthor())
                .createDate(newPost.getCreateDate())
                .updateDate(newPost.getUpdateDate())
                .isUpdated(newPost.isUpdated())
                .message("Post created successfully")
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable UUID postId,
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody PostDTO postDTO
    ) {
        postService.isAuthor(postId, auth.getUsername());
        PostEntity updatedPost = postService.updatePost(postId, postDTO);
        PostResponse responseDTO = PostResponse.builder()
                .id(updatedPost.getId())
                .title(updatedPost.getTitle())
                .content(updatedPost.getContent())
                .author(updatedPost.getAuthor())
                .createDate(updatedPost.getCreateDate())
                .updateDate(updatedPost.getUpdateDate())
                .isUpdated(updatedPost.isUpdated())
                .message("Post updated successfully")
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable UUID postId,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        //TODO 엔티티작성자와 auth유저가 동일한지 검증
        return ResponseEntity.ok("Hello, World!");
    }
}
