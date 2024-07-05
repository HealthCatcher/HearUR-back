package com.example.hearurbackend.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PostDTO {
    private UUID id;
    private String category;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createDate;

    public PostDTO(UUID id, String category, String title, String author, LocalDateTime createDate) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
    }
}
