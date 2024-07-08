package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.comment.CommentDto;
import com.example.hearurbackend.dto.oauth.CustomOAuth2User;
import com.example.hearurbackend.entity.Comment;
import com.example.hearurbackend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/community")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "댓글 작성")
    @PostMapping("/post/{postNo}/comment")
    public ResponseEntity<?> createComment(
            @PathVariable Long postNo,
            @RequestBody CommentDto commentDTO,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        try {
            Comment newComment = commentService.createComment(postNo, auth.getUsername(), commentDTO);
            String commentId = newComment.getId().toString();
            URI postUri = URI.create("/community/post/" + postNo + "/comment/" + commentId);
            return ResponseEntity.created(postUri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable UUID commentId,
            @RequestBody CommentDto commentDTO,
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
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(
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
