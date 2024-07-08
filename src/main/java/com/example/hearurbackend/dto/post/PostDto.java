package com.example.hearurbackend.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private Long no;
    private String category;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createDate;

    public PostDto(Long no, String category, String title, String author, LocalDateTime createDate) {
        this.no = no;
        this.category = category;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
    }
}
