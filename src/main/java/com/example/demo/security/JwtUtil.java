package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret:Amypo1234567890Amypo1234567890Amypo1234567890}")
    private String secret;
    
    @Value("${jwt.expiration-ms:86400000}")
    private long expirationMs;
    
    private Key key;
    
    @PostConstruct
    public void init() {
        // Ensure secret is at least 32 characters
        if (secret == null || secret.trim().isEmpty()) {
            secret = "Amypo1234567890Amypo1234567890Amypo1234567890";
        }
        
        if (secret.length() < 32) {
            // Pad with characters to reach 32
            StringBuilder sb = new StringBuilder(secret);
            while (sb.length() < 32) {
                sb.append("0");
            }
            secret = sb.toString();
        } else if (secret.length() > 64) {
            secret = secret.substring(0, 64);
        }
        
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
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