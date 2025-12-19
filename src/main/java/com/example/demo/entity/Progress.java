package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "progress",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "micro_lesson_id"})
    }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    private MicroLesson microLesson;

    @Column(nullable = false)
    private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED

    @Column(name = "progress_percent", nullable = false)
    private Integer progressPercent;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    private BigDecimal score;

    @PrePersist
    protected void onCreate() {
        this.lastAccessedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastAccessedAt = LocalDateTime.now();
    }
}
