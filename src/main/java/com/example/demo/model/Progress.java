package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "progress",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "micro_lesson_id"})
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    private MicroLesson microLesson;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false)
    @Builder.Default
    private String status = "NOT_STARTED";

    @NotNull
    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private Integer progressPercent;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastAccessedAt = LocalDateTime.now();

        if ("COMPLETED".equals(this.status)) {
            this.progressPercent = 100;
            if (this.completedAt == null) {
                this.completedAt = LocalDateTime.now();
            }
        }
    }

    // 🔥 REQUIRED BY TEST
    public void prePersist() {
        onUpdate();
    }
}
