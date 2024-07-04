package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.CommentDTO;
import com.example.hearurbackend.dto.CommentResponse;
import com.example.hearurbackend.dto.CustomOAuth2User;
import com.example.hearurbackend.entity.CommentEntity;
import com.example.hearurbackend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/community")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "댓글 작성")
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable UUID postId,
            @RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        CommentEntity newComment = commentService.createComment(postId, auth.getUsername(), commentDTO);
        CommentResponse responseDTO = CommentResponse.builder()
                .id(newComment.getId())
                .content(newComment.getContent())
                .author(newComment.getAuthor())
                .createDate(newComment.getCreateDate())
                .isUpdated(newComment.isUpdated())
                .post(newComment.getPost())
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable UUID postId,
            @PathVariable UUID commentId
    ) {
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable UUID postId, @PathVariable UUID commentId) {
        return ResponseEntity.ok("Hello, World!");
    }
}
