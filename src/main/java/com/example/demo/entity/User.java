package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(unique = true)
    private String email;
    
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @Column(nullable = false)
    @Builder.Default
    private String role = "LEARNER";
    
    @Column(name = "preferred_learning_style", length = 50)
    private String preferredLearningStyle;
    
    @Column(name = "created_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null || this.role.trim().isEmpty()) {
            this.role = "LEARNER";
        }
    }
    
    // This prevents id from being accepted in request body
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }
    
    // This allows id to be set internally (by Hibernate)
    public void setId(Long id) {
        this.id = id;
    }
}