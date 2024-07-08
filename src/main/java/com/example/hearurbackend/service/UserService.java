package com.example.hearurbackend.service;

import com.example.hearurbackend.entity.User;
import com.example.hearurbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User getUser(String username) {
        return userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void changeNickname(String username, String nickname) {
        User user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.changeNickname(nickname);
        userRepository.save(user);
    }
}
