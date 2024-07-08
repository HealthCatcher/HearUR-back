package com.example.hearurbackend.service;

import com.example.hearurbackend.domain.UserRole;
import com.example.hearurbackend.dto.user.UserDto;
import com.example.hearurbackend.entity.User;
import com.example.hearurbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Transactional
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }



    public void changeNickname(String username, String nickname) {
        User user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.changeNickname(nickname);
        userRepository.save(user);
    }
}
