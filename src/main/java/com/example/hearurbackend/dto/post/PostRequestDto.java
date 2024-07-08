package com.example.hearurbackend.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostRequestDto {
    private String title;
    private String category;
    private String content;
}
