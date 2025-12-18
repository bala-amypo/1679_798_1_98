package com.example.demo.entity;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
@Builder
@Entity
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
@JoinColumn(name = "user_id", nullable = false)
    private User user;
     @ManyToOne
    @JoinColumn(name = "micro_Lesson_id", nullable = false)
    private Microlesson microLesson;
    private String status;
    private Integer progressPercent;
    private LocalDateTime lastAccessedAt;
    private BigDecimal score;
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
    public Microlesson getMicroLesson() {
        return microLesson;
    }
    public void setMicroLesson(Microlesson microLesson) {
        this.microLesson = microLesson;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getProgressPercent() {
        return progressPercent;
    }
    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
    }
    public LocalDateTime getLastAccessedAt() {
        return lastAccessedAt;
    }
    public void setLastAccessedAt(LocalDateTime lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }
    public BigDecimal getScore() {
        return score;
    }
    public void setScore(BigDecimal score) {
        this.score = score;
    }
    public Progress() {
    }
    public Progress(Long id, User user, Microlesson microLesson, String status, Integer progressPercent,
            LocalDateTime lastAccessedAt, BigDecimal score) {
        this.id = id;
        this.user = user;
        this.microLesson = microLesson;
        this.status = status;
        this.progressPercent = progressPercent;
        this.lastAccessedAt = lastAccessedAt;
        this.score = score;
    }
    @PrePersist
     private void prePersist() {
        if (lastAccessedAt == null) {
            lastAccessedAt = LocalDateTime.now();
        }
        if (progressPercent != null) {
            if (progressPercent < 0 || progressPercent > 100) {
                throw new IllegalStateException("progressPercent must be between 0 and 100");
            }
        }
        if ("COMPLETED".equals(status)) {
            progressPercent = 100;
        }
    }
}
