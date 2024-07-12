package com.example.hearurbackend.entity.community;

import com.example.hearurbackend.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private boolean isUpdated;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(String content, String author, LocalDateTime createDate, Post post) {
        this.content = content;
        this.author = author;
        this.createDate = createDate;
        this.isUpdated = false;
        this.post = post;
    }

    public void updateComment(String content) {
        this.content = content;
        this.isUpdated = true;
    }
}
