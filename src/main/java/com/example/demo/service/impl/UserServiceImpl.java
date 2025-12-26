package com.example.demo.service.impl;

import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Override
    public User register(User user) {
        try {
            log.info("Registering user with email: {}", user.getEmail());
            
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            
            // Basic validation
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                throw new RuntimeException("Email is required");
            }
            
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new RuntimeException("Password is required");
            }
            
            if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
                throw new RuntimeException("Full name is required");
            }
            
            // Encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            // Set default role if not provided
            if (user.getRole() == null || user.getRole().trim().isEmpty()) {
                user.setRole("LEARNER");
            }
            
            User savedUser = userRepository.save(user);
            log.info("User registered successfully: {}", savedUser.getEmail());
            return savedUser;
            
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage(), e);
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }
    
    @Override
    public AuthResponse login(String email, String password) {
        try {
            log.info("Login attempt for email: {}", email);
            
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
            
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("role", user.getRole());
            
            String token = jwtUtil.generateToken(claims, user.getEmail());
            
            log.info("Login successful for user: {}", email);
            
            return AuthResponse.builder()
                    .accessToken(token)
                    .userId(user.getId())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .message("Login successful")
                    .build();
                    
        } catch (Exception e) {
            log.error("Login failed for email {}: {}", email, e.getMessage());
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}