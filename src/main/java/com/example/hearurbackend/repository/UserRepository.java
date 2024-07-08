package com.example.hearurbackend.repository;

import com.example.hearurbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
