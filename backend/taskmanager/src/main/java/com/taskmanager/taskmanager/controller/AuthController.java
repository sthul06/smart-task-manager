package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.auth.AuthRequest;
import com.taskmanager.taskmanager.auth.AuthResponse;
import com.taskmanager.taskmanager.auth.JwtUtil;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // REGISTER API
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // LOGIN API
    @PostMapping("/login")
    public AuthResponse loginUser(
            @RequestBody AuthRequest authRequest
    ) {

        User user = userRepository.findByEmail(
                authRequest.getEmail()
        );

        if(user == null) {
            throw new RuntimeException("User Not Found");
        }

        boolean passwordMatch =
                passwordEncoder.matches(
                        authRequest.getPassword(),
                        user.getPassword()
                );

        if(!passwordMatch) {
            throw new RuntimeException("Invalid Password");
        }

        String token =
                JwtUtil.generateToken(user.getEmail());
        System.out.println("NEW TOKEN = " + token);

        return new AuthResponse(token);
    }
}

