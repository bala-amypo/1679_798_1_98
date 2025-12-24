package com.example.demo.controller;

import com.example.demo.dto.AuthResponse;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestParam String email,
                              @RequestParam String password) {
        return service.login(email, password);
    }
}
