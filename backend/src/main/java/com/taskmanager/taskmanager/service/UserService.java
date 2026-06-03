package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // Add User
    public User saveUser(User user) {

        // Encrypt Password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}