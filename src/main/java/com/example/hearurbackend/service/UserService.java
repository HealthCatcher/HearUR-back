package com.example.hearurbackend.service;

import com.example.hearurbackend.domain.UserRole;
import com.example.hearurbackend.dto.UserDTO;
import com.example.hearurbackend.entity.User;
import com.example.hearurbackend.repository.UserRepository;
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
    public User registerUser(UserDTO userDTO){
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Given user already exists");
        }
        User user = new User(
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getName(),
                userDTO.getUsername(),
                UserRole.ROLE_USER
        );
        return userRepository.save(user);
    }

    @Transactional
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public void changePassword(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.changePassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }
}
