package com.example.hearurbackend.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String content;
    private String author;
    private String createDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private PostEntity post;
}
