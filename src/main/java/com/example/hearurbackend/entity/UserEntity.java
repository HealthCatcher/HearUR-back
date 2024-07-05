package com.example.hearurbackend.entity;

import com.example.hearurbackend.domain.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password; // normal user
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserEntity(String username, String password, String name, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void createOAuthUser(String username, String email, String name, UserRole role) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public void updateOAuthUser(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
