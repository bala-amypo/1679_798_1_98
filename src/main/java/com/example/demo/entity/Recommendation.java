package com.example.demo.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name="recommendations")
@Builder
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User user;
    private LocalDateTime generatedAt;
    private String recommendedLessonIds;
    private String basisSnapshot;
    private BigDecimal confidenceScore;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
    public String getRecommendedLessonIds() {
        return recommendedLessonIds;
    }
    public void setRecommendedLessonIds(String recommendedLessonIds) {
        this.recommendedLessonIds = recommendedLessonIds;
    }
    public String getBasisSnapshot() {
        return basisSnapshot;
    }
    public void setBasisSnapshot(String basisSnapshot) {
        this.basisSnapshot = basisSnapshot;
    }
    public BigDecimal getConfidenceScore() {
        return confidenceScore;
    }
    public void setConfidenceScore(BigDecimal confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
    public Recommendation() {
    }
    public Recommendation(Long id, User user, LocalDateTime generatedAt, String recommendedLessonIds,
            String basisSnapshot, BigDecimal confidenceScore) {
        this.id = id;
        this.user = user;
        this.generatedAt = generatedAt;
        this.recommendedLessonIds = recommendedLessonIds;
        this.basisSnapshot = basisSnapshot;
        this.confidenceScore = confidenceScore;
    }
    @PrePersist
    protected void onCreate() {
        if (generatedAt == null) {
            generatedAt = LocalDateTime.now();
        }
    }
}
