package com.example.hearurbackend.dto;

import com.example.hearurbackend.entity.PostEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CommentResponse {
    private UUID id;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private boolean isUpdated;
    private PostEntity post;
}
