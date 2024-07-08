package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.oauth.CustomOAuth2User;
import com.example.hearurbackend.dto.user.UserDto;
import com.example.hearurbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "별명 변경")
    @PutMapping("/nickname")
    public ResponseEntity<?> changeNickname(
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody UserDto userDTO
    ) {
        userService.changeNickname(auth.getUsername(), userDTO.getNickname());
        return ResponseEntity.noContent().build();
    }
}
