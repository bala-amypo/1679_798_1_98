package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDateTime generatedAt;

    private String recommendedLessonIds; // comma-separated IDs

    private String basisSnapshot;

    private Double confidenceScore;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public String getRecommendedLessonIds() { return recommendedLessonIds; }
    public void setRecommendedLessonIds(String recommendedLessonIds) { this.recommendedLessonIds = recommendedLessonIds; }

    public String getBasisSnapshot() { return basisSnapshot; }
    public void setBasisSnapshot(String basisSnapshot) { this.basisSnapshot = basisSnapshot; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
}
