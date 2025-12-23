package com.example.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column
    private String preferredLearningStyle;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null || this.role.isBlank()) {
            this.role = "LEARNER";
        }
    }

    // Convenience constructor (without id and createdAt)
    public User(String fullName, String email, String password, String role, String preferredLearningStyle) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = (role == null || role.isBlank()) ? "LEARNER" : role;
        this.preferredLearningStyle = preferredLearningStyle;
    }

    // Optional helper methods for JWT or security
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

    public boolean isInstructor() {
        return "INSTRUCTOR".equalsIgnoreCase(this.role);
    }

    public boolean isLearner() {
        return "LEARNER".equalsIgnoreCase(this.role);
    }
}
