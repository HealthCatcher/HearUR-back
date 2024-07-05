package com.example.hearurbackend.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CommentDTO {
    private UUID id;
    private String author;
    private String content;
    private LocalDateTime createDate;
    private boolean isUpdated;

    public CommentDTO(UUID id, String author, String content, LocalDateTime createDate, boolean isUpdated) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createDate = createDate;
        this.isUpdated = isUpdated;
    }
}
