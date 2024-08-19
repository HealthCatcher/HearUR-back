package com.example.hearurbackend.repository;

import com.example.hearurbackend.entity.experience.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExperienceNoticeRepository extends JpaRepository<Notice, UUID> {
}
