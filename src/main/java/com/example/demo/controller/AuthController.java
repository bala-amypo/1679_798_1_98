package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Authentication", description = "User registration and login APIs")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @Operation(summary = "Login and get JWT token")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String token = userService.login(request.get("email"), request.get("password"));
        return Map.of("token", token);
    }
}
