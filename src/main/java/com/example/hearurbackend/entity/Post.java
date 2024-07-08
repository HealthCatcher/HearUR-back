package com.example.hearurbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String category;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isUpdated;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String category, String title, String content, String author, LocalDateTime createDate, LocalDateTime updateDate, boolean isUpdated) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.isUpdated = isUpdated;
    }

    public void updatePost(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
        this.updateDate = LocalDateTime.now();
        this.isUpdated = true;
    }
}
