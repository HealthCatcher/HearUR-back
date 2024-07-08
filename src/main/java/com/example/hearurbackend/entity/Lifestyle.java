package com.example.hearurbackend.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Lifestyle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String exerciseFrequency;

    private String dietaryHabits;

    private Float sleepDuration;

    private Boolean smokingStatus;

    private String alcoholConsumption;

    // Getters and Setters
}