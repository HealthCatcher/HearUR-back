package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.CommentDTO;
import com.example.hearurbackend.dto.CustomOAuth2User;
import com.example.hearurbackend.dto.PostDTO;
import com.example.hearurbackend.dto.PostResponse;

import com.example.hearurbackend.entity.Post;
import com.example.hearurbackend.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<?> getPostList() {
        try {
            List<PostDTO> postList = postService.getPostList();
            return ResponseEntity.ok(postList);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostDetail(
            @PathVariable UUID postId
    ) {
        try {
            Post post = postService.getPost(postId);
            List<CommentDTO> commentDTOList = post.getComments().stream()
                    .map(comment -> new CommentDTO(comment.getId(), comment.getAuthor(), comment.getContent(), comment.getCreateDate(), comment.isUpdated()))
                    .toList();
            PostResponse responseDTO = PostResponse.builder()
                    .id(post.getId())
                    .category(post.getCategory())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getAuthor())
                    .createDate(post.getCreateDate())
                    .updateDate(post.getUpdateDate())
                    .isUpdated(post.isUpdated())
                    .message("Post found successfully")
                    .comments(commentDTOList)
                    .build();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "게시글 작성")
    @PostMapping("/post")
    public ResponseEntity<?> createPost(
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody PostDTO postDTO
    ) {
        try {
            Post newPost = postService.createPost(postDTO, auth.getUsername());
            String postId = newPost.getId().toString();
            URI postUri = URI.create("/community/post/" + postId);
            return ResponseEntity.created(postUri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/post/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable UUID postId,
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody PostDTO postDTO
    ) {
        try {
            postService.updatePost(postId, postDTO, auth.getUsername());
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable UUID postId,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        try {
            postService.deletePost(postId, auth.getUsername());
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}