package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "generated_at", updatable = false)
    private LocalDateTime generatedAt;

    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false)
    private String recommendedLessonIds;

    @Size(max = 2000)
    private String basisSnapshot;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    @Column(precision = 3, scale = 2, nullable = false)
    private BigDecimal confidenceScore;

    @PrePersist
    protected void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }

    // 🔥 REQUIRED BY TEST
    public void prePersist() {
        onCreate();
    }
}
