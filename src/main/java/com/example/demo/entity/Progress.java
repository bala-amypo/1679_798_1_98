package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    private MicroLesson microLesson;

    @NotBlank
    @Size(max = 20)
    private String status;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer progressPercent;

    private LocalDateTime lastAccessedAt;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    /* ---------- Lifecycle ---------- */

    @PrePersist
    public void prePersist() {
        this.lastAccessedAt = LocalDateTime.now();
        if (this.status == null || this.status.isBlank()) {
            this.status = "NOT_STARTED";
            this.progressPercent = 0;
        }
    }
}
