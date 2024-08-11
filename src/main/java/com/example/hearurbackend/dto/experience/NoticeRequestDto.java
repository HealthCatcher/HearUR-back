package com.example.hearurbackend.dto.experience;

import com.example.hearurbackend.dto.comment.CommentDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class NoticeRequestDto {
    private final Long no;
    private final String category;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;
    private final boolean isUpdated;
    private final List<CommentDto> comments;
    private final int views;
    private final int likes;
}
