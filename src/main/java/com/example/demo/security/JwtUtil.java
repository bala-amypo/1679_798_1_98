package com.example.demo.security;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtUtil {

    public String generateToken(Map<String, Object> claims, String subject) {
        return "dummy-token";
    }

    public boolean validateToken(String token) {
        return true;
    }
}
