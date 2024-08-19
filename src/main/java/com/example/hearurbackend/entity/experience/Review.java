package com.example.hearurbackend.entity.experience;

import com.example.hearurbackend.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class Review {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "experience_notice_id")
    private Notice experienceNotice;
    private String content;
    private LocalDateTime createdAt;
}
