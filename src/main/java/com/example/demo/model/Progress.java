package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private MicroLesson microLesson;

    @Column(nullable = false)
    private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED

    @Column(nullable = false)
    private Integer progressPercent;

    @Column
    private BigDecimal score;

    @Column(nullable = false)
    private LocalDateTime lastAccessedAt;

    public Progress() {
    }

    @PrePersist
    protected void onCreate() {
        this.lastAccessedAt = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MicroLesson getMicroLesson() {
        return microLesson;
    }

    public void setMicroLesson(MicroLesson microLesson) {
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public LocalDateTime getLastAccessedAt() {
        return lastAccessedAt;
    }
    @PrePersist
public void onAccess() {
    this.lastAccessedAt = LocalDateTime.now();
}
}
