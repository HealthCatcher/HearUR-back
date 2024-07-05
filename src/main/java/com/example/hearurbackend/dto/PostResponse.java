package com.example.hearurbackend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class PostResponse {
    private UUID id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isUpdated;
    private String message;
}