package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    
    private SecretKey secretKey;
    private long expirationMs;
    
    // Constructor that handles missing properties gracefully
    public JwtUtil(@Value("${jwt.secret:#{null}}") String secret,
                   @Value("${jwt.expiration-ms:#{null}}") Long expirationMs) {
        
        // Handle missing secret with fallback
        if (secret == null || secret.trim().isEmpty()) {
            secret = "Amypo1234567890Amypo1234567890Amypo1234567890"; // Using your password as base
        }
        
        // Ensure secret is at least 32 characters
        if (secret.length() < 32) {
            // Pad with characters to reach 32
            StringBuilder sb = new StringBuilder(secret);
            while (sb.length() < 32) {
                sb.append("0");
            }
            secret = sb.toString();
        } else if (secret.length() > 64) {
            // Trim if too long
            secret = secret.substring(0, 64);
        }
        
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs != null ? expirationMs : 86400000L; // Default 24 hours
    }
    
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    // Helper methods that might be used in tests
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public String getEmailFromToken(String token) {
        return extractClaims(token).getSubject();
    }
    
    public String getRoleFromToken(String token) {
        return extractClaims(token).get("role", String.class);
    }
    
    public Long getUserIdFromToken(String token) {
        return extractClaims(token).get("userId", Long.class);
    }
}