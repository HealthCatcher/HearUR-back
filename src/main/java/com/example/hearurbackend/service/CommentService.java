package com.example.hearurbackend.service;

import com.example.hearurbackend.dto.CommentDTO;
import com.example.hearurbackend.entity.CommentEntity;
import com.example.hearurbackend.entity.PostEntity;
import com.example.hearurbackend.repository.CommentRepository;
import com.example.hearurbackend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public CommentEntity createComment(UUID postId, String username, CommentDTO commentDTO) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("Post not found"));

        CommentEntity newComment = CommentEntity.builder()
                .content(commentDTO.getContent())
                .author(username)
                .createDate(LocalDateTime.now())
                .post(post)
                .build();

        return commentRepository.save(newComment);
    }

    public void updateComment(String username, UUID commentId, CommentDTO commentDTO) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment not found"));

        if (!comment.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You are not the author of this comment");
        }

        comment.updateComment(commentDTO.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(UUID commentId, String username) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + commentId));
        if (!comment.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You are not the author of this post.");
        }
        commentRepository.delete(comment);
    }
}
