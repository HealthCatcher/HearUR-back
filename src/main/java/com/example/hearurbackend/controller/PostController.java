package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.CustomOAuth2User;
import com.example.hearurbackend.dto.PostDTO;
import com.example.hearurbackend.entity.PostEntity;
import com.example.hearurbackend.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/community")
public class PostController {
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
    public ResponseEntity<String> createPost(
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody PostDTO postDTO
    ) {
        auth.getUsername();
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/post/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable UUID postId,
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody PostDTO postDTO
    ) {
        //TODO 엔티티작성자와 auth유저가 동일한지 검증
        return ResponseEntity.ok("Hello, World!");
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
