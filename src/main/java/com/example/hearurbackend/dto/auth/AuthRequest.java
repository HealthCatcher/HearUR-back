package com.example.hearurbackend.dto.auth;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String provider;
    private String accessToken;
}
