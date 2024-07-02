package com.example.hearurbackend.dto;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String provider;
    private String accessToken;
}
