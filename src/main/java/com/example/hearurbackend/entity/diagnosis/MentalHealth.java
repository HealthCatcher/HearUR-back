package com.example.hearurbackend.entity.diagnosis;

import com.example.hearurbackend.entity.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class MentalHealth {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer stressLevel;

    private String moodStatus;

    // Getters and Setters
}
