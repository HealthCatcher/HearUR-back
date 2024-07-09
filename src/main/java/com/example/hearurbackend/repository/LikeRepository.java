package com.example.hearurbackend.repository;

import com.example.hearurbackend.entity.Like;
import com.example.hearurbackend.entity.Post;
import com.example.hearurbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
