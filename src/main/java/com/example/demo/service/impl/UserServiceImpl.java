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
            log.info("=== USER REGISTRATION START ===");
            log.info("Received user data - Email: {}, Name: {}, Role: {}", 
                     user.getEmail(), user.getFullName(), user.getRole());
            
            // Check if email already exists
            if (userRepository.existsByEmail(user.getEmail())) {
                log.error("Registration failed: Email already exists - {}", user.getEmail());
                throw new RuntimeException("Email already exists");
            }
            
            // Validate required fields
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                throw new RuntimeException("Email is required");
            }
            
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new RuntimeException("Password is required");
            }
            
            if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
                throw new RuntimeException("Full name is required");
            }
            
            // Important: Reset ID to null to avoid ID conflicts
            user.setId(null);
            
            // Hash the password
            String rawPassword = user.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encodedPassword);
            
            // Set default role if not provided
            if (user.getRole() == null || user.getRole().trim().isEmpty()) {
                user.setRole("LEARNER");
                log.info("Setting default role: LEARNER");
            }
            
            // Ensure createdAt is null (will be set by @PrePersist)
            user.setCreatedAt(null);
            
            // Save user to database
            log.info("Saving user to database...");
            User savedUser = userRepository.save(user);
            
            log.info("=== USER REGISTRATION SUCCESS ===");
            log.info("User registered successfully - ID: {}, Email: {}, Role: {}", 
                     savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
            
            return savedUser;
            
        } catch (Exception e) {
            log.error("=== USER REGISTRATION FAILED ===");
            log.error("Error details: {}", e.getMessage());
            log.error("Stack trace:", e);
            
            // Check for common database errors
            if (e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException("Email already registered");
            } else if (e.getMessage().contains("Data truncation")) {
                throw new RuntimeException("Input data too long for field");
            } else if (e.getMessage().contains("cannot be null")) {
                throw new RuntimeException("Required field is missing");
            }
            
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }
    
    @Override
    public AuthResponse login(String email, String password) {
        try {
            log.info("=== USER LOGIN ATTEMPT ===");
            log.info("Login attempt for email: {}", email);
            
            // Find user by email
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Login failed: User not found with email {}", email);
                    return new RuntimeException("Invalid email or password");
                });
            
            log.info("User found - ID: {}, Role: {}", user.getId(), user.getRole());
            
            // Verify password
            boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
            if (!passwordMatches) {
                log.error("Login failed: Password incorrect for user {}", email);
                throw new RuntimeException("Invalid email or password");
            }
            
            // Generate JWT token
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("role", user.getRole());
            
            String token = jwtUtil.generateToken(claims, user.getEmail());
            log.info("JWT token generated successfully for user: {}", email);
            
            // Build response
            AuthResponse response = AuthResponse.builder()
                    .accessToken(token)
                    .userId(user.getId())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .message("Login successful")
                    .build();
            
            log.info("=== USER LOGIN SUCCESS ===");
            log.info("User logged in successfully - ID: {}, Email: {}", user.getId(), email);
            
            return response;
                    
        } catch (Exception e) {
            log.error("=== USER LOGIN FAILED ===");
            log.error("Login error for email {}: {}", email, e.getMessage());
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
    
    @Override
    public User findById(Long id) {
        try {
            log.info("Finding user by ID: {}", id);
            return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found");
                });
        } catch (Exception e) {
            log.error("Error finding user by ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
    
    @Override
    public User findByEmail(String email) {
        try {
            log.info("Finding user by email: {}", email);
            return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFoundException("User not found");
                });
        } catch (Exception e) {
            log.error("Error finding user by email {}: {}", email, e.getMessage());
            throw e;
        }
    }
    
    // Optional: Helper method to check if email exists
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    // Optional: Helper method to get all users (for debugging)
    public java.util.List<User> getAllUsers() {
        log.info("Retrieving all users");
        return userRepository.findAll();
    }
}