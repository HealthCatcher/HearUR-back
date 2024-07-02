package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.CustomOAuth2User;
import com.example.hearurbackend.dto.UserDTO;
import com.example.hearurbackend.entity.PostEntity;
import com.example.hearurbackend.entity.UserEntity;
import com.example.hearurbackend.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @Operation(summary = "게시글 목록 조회")
    @GetMapping("/post-list")
    @ResponseBody
    public ResponseEntity<String> getPostList() {
        List<PostEntity> postList = communityService.getPostList();
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<String> getPostDetail(@PathVariable UUID postId) {
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "게시글 작성")
    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<String> createPost(@AuthenticationPrincipal CustomOAuth2User auth) {
        auth.getUsername();
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<String> updatePost(@PathVariable UUID postId) {
        return ResponseEntity.ok("Hello, World!");
    }
}
