package com.example.hearurbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDTO {
    private String title;
    private String category;
    private String content;
}
