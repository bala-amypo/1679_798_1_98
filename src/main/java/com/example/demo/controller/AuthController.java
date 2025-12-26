package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import com.example.demo.dto.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        service.register(user);
        return ResponseEntity.ok(new AuthResponse("REGISTERED"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
        String token = service.login(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
