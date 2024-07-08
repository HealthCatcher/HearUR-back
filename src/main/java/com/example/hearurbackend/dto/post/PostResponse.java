package com.example.hearurbackend.dto.post;

import com.example.hearurbackend.dto.comment.CommentDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostResponse {
    private Long no;
    private String category;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isUpdated;
    private List<CommentDto> comments;
    private String message;
}
