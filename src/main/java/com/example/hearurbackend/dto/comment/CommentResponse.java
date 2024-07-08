package com.example.hearurbackend.dto.comment;

import com.example.hearurbackend.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CommentResponse {
    private UUID id;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private boolean isUpdated;
    private Post post;
}
