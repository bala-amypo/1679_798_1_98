
package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    
    @NotBlank(message = "Recommended lesson IDs are required")
    @Size(max = 1000)
    @Column(nullable = false)
    private String recommendedLessonIds;
    
    @Size(max = 2000)
    private String basisSnapshot;
    
    @NotNull(message = "Confidence score is required")
    @DecimalMin(value = "0.0", message = "Confidence score cannot be less than 0.0")
    @DecimalMax(value = "1.0", message = "Confidence score cannot exceed 1.0")
    @Column(precision = 3, scale = 2, nullable = false)
    private BigDecimal confidenceScore;
    
    @PrePersist
    protected void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }
}