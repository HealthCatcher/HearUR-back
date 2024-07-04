package com.example.hearurbackend.service;

import com.example.hearurbackend.domain.UserRole;
import com.example.hearurbackend.dto.UserDTO;
import com.example.hearurbackend.entity.UserEntity;
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
    public UserEntity registerUser(UserDTO userDTO){
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Given user already exists");
        }
        UserEntity user = new UserEntity(
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getName(),
                userDTO.getUsername(),
                UserRole.ROLE_USER.toString()
        );
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
