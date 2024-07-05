package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.UserDTO;
import com.example.hearurbackend.entity.UserEntity;
import com.example.hearurbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "회원가입")
    @PostMapping("/auth/signup")
    public ResponseEntity<String> register(
            @RequestBody UserDTO userDTO
    ) {
        try {
            UserEntity newUser = userService.registerUser(userDTO);
            return ResponseEntity.created(URI.create("/users/"+newUser.getUsername())).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "비밀번호 변경")
    @PostMapping("/auth/password")
    public ResponseEntity<String> changePassword(
            @RequestBody UserDTO userDTO
    ) {
        try {
            userService.changePassword(userDTO);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
