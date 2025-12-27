package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;   // ✅ ADD THIS
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "courses",
    uniqueConstraints = @UniqueConstraint(columnNames = {"title", "instructor_id"})
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false)
    private String title;

    @Size(max = 500)
    private String description;

    // ✅ FIX IS HERE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    @JsonIgnore
    private User instructor;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String category;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 🔥 REQUIRED BY TEST
    public void prePersist() {
        onCreate();
    }
}
