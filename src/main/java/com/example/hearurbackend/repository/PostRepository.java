package com.example.hearurbackend.repository;

import com.example.hearurbackend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId")
    int countLikesByPostId(@Param("postId") Long postId);
}
