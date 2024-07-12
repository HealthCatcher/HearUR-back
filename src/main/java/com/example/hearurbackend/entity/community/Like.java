package com.example.hearurbackend.entity.community;

import com.example.hearurbackend.entity.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "post_likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post", nullable = false)
    private Post post;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
