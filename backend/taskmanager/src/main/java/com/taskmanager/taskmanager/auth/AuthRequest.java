package com.taskmanager.taskmanager.auth;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;
}