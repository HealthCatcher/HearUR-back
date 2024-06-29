package com.example.hearurbackend.service;

import com.example.hearurbackend.dto.UserDTO;
import com.example.hearurbackend.entity.UserEntity;
import com.example.hearurbackend.jwt.JWTUtil;
import com.example.hearurbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthService(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public String generateJwtToken(String username) {
        // JWT 토큰 생성 및 반환
        String role = "ROLE_USER";
        return jwtUtil.createJwt(username, role, 60 * 60 * 60L);
    }

    public UserEntity saveUser(String username, String email, String name, String role) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            // 새로운 사용자 생성
            userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setEmail(email);
            userEntity.setName(name);
            userEntity.setRole(role);
        } else {
            // 이미 존재하는 사용자 업데이트
            userEntity.setEmail(email);
            userEntity.setName(name);
        }
        return userRepository.save(userEntity);
    }
}
