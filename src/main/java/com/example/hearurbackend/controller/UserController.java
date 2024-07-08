package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.user.UserDto;
import com.example.hearurbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> changeNickname(@RequestBody UserDto userDTO) {
        try {
            userService.changeNickname(userDTO);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
