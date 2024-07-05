package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.CommentDTO;
import com.example.hearurbackend.dto.CommentResponse;
import com.example.hearurbackend.dto.CustomOAuth2User;
import com.example.hearurbackend.entity.CommentEntity;
import com.example.hearurbackend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<?> createComment(
            @PathVariable UUID postId,
            @RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        try {
            CommentEntity newComment = commentService.createComment(postId, auth.getUsername(), commentDTO);
            String commentId = newComment.getId().toString();
            URI postUri = URI.create("/community/post/" + postId + "/comment/" + commentId);
            return ResponseEntity.created(postUri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable UUID postId,
            @PathVariable UUID commentId,
            @RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        try {
            commentService.updateComment(auth.getUsername(), commentId, commentDTO);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable UUID postId,
            @PathVariable UUID commentId,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        try {
            commentService.deleteComment(commentId, auth.getUsername());
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
