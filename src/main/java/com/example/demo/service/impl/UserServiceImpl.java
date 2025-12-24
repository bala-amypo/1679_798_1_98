package com.example.demo.service.impl;

import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;

public class UserServiceImpl {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwt;

    public UserServiceImpl(UserRepository repo, BCryptPasswordEncoder encoder, JwtUtil jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public User register(User u) {
        if (u == null) throw new RuntimeException();
        if (repo.existsByEmail(u.getEmail())) throw new RuntimeException();
        u.setPassword(encoder.encode(u.getPassword()));
        return repo.save(u);
    }

    public AuthResponse login(String email, String rawPassword) {
        User u = repo.findByEmail(email).orElseThrow();
        if (!encoder.matches(rawPassword, u.getPassword())) throw new RuntimeException();
        return new AuthResponse(jwt.generateToken(new HashMap<>(), email));
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }
}
