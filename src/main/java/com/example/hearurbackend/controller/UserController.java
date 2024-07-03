package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.UserDTO;
import com.example.hearurbackend.entity.UserEntity;
import com.example.hearurbackend.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        JSONObject responseData = new JSONObject();
        try {
            UserEntity newUser = userService.registerUser(userDTO);
            responseData.put("status_code", "200");
            responseData.put("userId", newUser.getUsername());
            return ResponseEntity.ok().body(responseData.toString());
        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }
    }
}
