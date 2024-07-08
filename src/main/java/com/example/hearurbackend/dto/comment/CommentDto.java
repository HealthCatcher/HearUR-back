package com.example.hearurbackend.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CommentDto {
    private UUID id;
    private String author;
    private String content;
    private LocalDateTime createDate;
    private boolean isUpdated;

    public CommentDto(UUID id, String author, String content, LocalDateTime createDate, boolean isUpdated) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createDate = createDate;
        this.isUpdated = isUpdated;
    }
}
