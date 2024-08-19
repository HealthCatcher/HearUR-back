package com.example.hearurbackend.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CommentResponseDto {
    private UUID id;
    private String author;
    private String content;
    private LocalDateTime createDate;
    private boolean isUpdated;

    public CommentResponseDto(UUID id, String author, String content, LocalDateTime createDate, boolean isUpdated) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createDate = createDate;
        this.isUpdated = isUpdated;
    }
}
