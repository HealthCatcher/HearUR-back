package com.example.hearurbackend.dto.review;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ReviewResponseDto {
    private UUID id;
    private String author;
    private String content;
    private LocalDateTime createDate;

    public ReviewResponseDto(UUID id, String author, String content, LocalDateTime createDate) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createDate = createDate;
    }
}
