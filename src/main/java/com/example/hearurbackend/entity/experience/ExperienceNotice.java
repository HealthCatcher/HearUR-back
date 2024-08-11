package com.example.hearurbackend.entity.experience;

import com.example.hearurbackend.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
public class ExperienceNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;
    private String title;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String company;
    private String content;
    private int price;
    private String option;
    @OneToMany(mappedBy = "experienceNotice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "experience_participants",
            joinColumns = @JoinColumn(name = "experience_id"),
            inverseJoinColumns = @JoinColumn(name = "username")
    )
    private List<User> participants = new ArrayList<>();
}
