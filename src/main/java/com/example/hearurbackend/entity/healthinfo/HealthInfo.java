package com.example.hearurbackend.entity.healthinfo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class HealthInfo {
    @Id
    private UUID id;
    private String category;
    private String content;
}
