
package com.example.demo.controller;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User createdUser = userService.register(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user and return JWT token")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(authResponse);
    }
}
