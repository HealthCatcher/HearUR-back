package com.example.hearurbackend.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PostDTO {
    private Long no;
    private String category;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createDate;

    public PostDTO(Long no, String category, String title, String author, LocalDateTime createDate) {
        this.no = no;
        this.category = category;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
    }
}
