package com.example.hearurbackend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PostResponse {
    private UUID id;
    private String category;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isUpdated;
    private List<CommentDTO> comments;
    private String message;
}
