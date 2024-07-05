package com.example.hearurbackend.dto;

import com.example.hearurbackend.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private UserRole role;
}
