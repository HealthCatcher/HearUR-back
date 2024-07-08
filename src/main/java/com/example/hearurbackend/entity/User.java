package com.example.hearurbackend.entity;

import com.example.hearurbackend.domain.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String password; // normal user
    private String name;
    private String nickname;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Integer age;

    private String gender;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<HealthRecord> healthRecords;

    @OneToOne(mappedBy = "user")
    private Lifestyle lifestyle;

    @OneToOne(mappedBy = "user")
    private MentalHealth mentalHealth;

    public User(String username, String password, String name, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.nickname = name;
    }

    public void createOAuthUser(String username, String email, String name, UserRole role) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.nickname = name;
    }

    public void updateOAuthUser(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
