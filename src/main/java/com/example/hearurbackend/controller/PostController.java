package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.comment.CommentDto;
import com.example.hearurbackend.dto.oauth.CustomOAuth2User;
import com.example.hearurbackend.dto.post.PostDto;
import com.example.hearurbackend.dto.post.PostResponse;

import com.example.hearurbackend.entity.Post;
import com.example.hearurbackend.service.PostService;
import com.example.hearurbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/community")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(
            PostService communityService,
            UserService userService
    ) {
        this.postService = communityService;
        this.userService = userService;
    }

    @Operation(summary = "게시글 목록 조회")
    @GetMapping("/post")
    public ResponseEntity<?> getPostList() {
        try {
            List<PostDto> postList = postService.getPostList();
            return ResponseEntity.ok(postList);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/post/{postNo}")
    public ResponseEntity<?> getPostDetail(
            @PathVariable Long postNo
    ) {
        try {
            Post post = postService.getPost(postNo);
            List<CommentDto> commentDTOList = post.getComments().stream()
                    .map(comment -> new CommentDto(comment.getId(), userService.getUser(comment.getAuthor()).getNickname(), comment.getContent(), comment.getCreateDate(), comment.isUpdated()))
                    .toList();
            PostResponse responseDTO = PostResponse.builder()
                    .no(post.getNo())
                    .category(post.getCategory())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(userService.getUser(post.getAuthor()).getNickname())
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
            @RequestBody PostDto postDTO
    ) {
        try {
            Post newPost = postService.createPost(postDTO, auth.getUsername());
            String postNo = newPost.getNo().toString();
            URI postUri = URI.create("/community/post/" + postNo);
            return ResponseEntity.created(postUri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/post/{postNo}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postNo,
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody PostDto postDTO
    ) {
        try {
            postService.updatePost(postNo, postDTO, auth.getUsername());
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/post/{postNo}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long postNo,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        try {
            postService.deletePost(postNo, auth.getUsername());
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}