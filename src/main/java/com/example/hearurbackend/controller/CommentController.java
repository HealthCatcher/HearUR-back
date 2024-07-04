package com.example.hearurbackend.controller;

import com.example.hearurbackend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> createComment(@PathVariable UUID postId) {
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable UUID postId, @PathVariable UUID commentId) {
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable UUID postId, @PathVariable UUID commentId) {
        return ResponseEntity.ok("Hello, World!");
    }
}
