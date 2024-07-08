package com.example.hearurbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class HealthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Float weight;

    private Integer bloodPressureSystolic;

    private Integer bloodPressureDiastolic;

    private Integer heartRate;

    private Float waistCircumference;

    private Float bodyFatPercentage;

    private Float bmi;

    @Column(nullable = false)
    private LocalDateTime recordDate;

    // Getters and Setters
}
