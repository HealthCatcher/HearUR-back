package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.AuthRequest;
import com.example.hearurbackend.entity.UserEntity;
import com.example.hearurbackend.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        // 모바일 앱에서 전송한 인증 정보를 받아서 처리
        String provider = request.getProvider();
        String providerId = request.getProviderId();
        String name = request.getName();
        String email = request.getEmail();

        String username = provider + " " + providerId;

        UserEntity newUser = authService.saveUser(username, email, name, "ROLE_USER");

        // AuthService를 통해 JWT 토큰 생성 및 반환
        String token = authService.generateJwtToken(newUser.getUsername());

        // JWT 토큰을 헤더에 담아 응답
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return ResponseEntity.ok().headers(headers).body("{\"code\": \"200\"}");
    }
}
