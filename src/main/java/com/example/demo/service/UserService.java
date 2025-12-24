package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.payload.AuthResponse;
import com.example.demo.exception.ResourceNotFoundException;

public interface UserService {

    // Registers a new user
    User register(User user);

    // Authenticates user and returns JWT token
    AuthResponse login(String email, String password);

    // Find user by ID
    User findById(Long id) throws ResourceNotFoundException;

    // Find user by email
    User findByEmail(String email) throws ResourceNotFoundException;
}
