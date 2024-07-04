package com.example.hearurbackend.service;

import com.example.hearurbackend.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService{
    private final CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void createComment(){
        System.out.println("Hello, World!");
    }
    public void updateComment(){
        System.out.println("Hello, World!");
    }
    public void deleteComment(){
        System.out.println("Hello, World!");
    }
}
