package com.example.hearurbackend.dto.experience;

import lombok.Getter;

@Getter
public class NoticeRequestDto {
    private String category;
    private String title;
    private String content;
    private String author;
    private String startDate;
    private String endDate;
    private boolean isUpdated;

    public NoticeRequestDto(String category, String title, String content, String author, String startDate, String endDate, boolean isUpdated) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isUpdated = isUpdated;
    }
}
