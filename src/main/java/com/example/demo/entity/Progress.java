package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "micro_lesson_id")
    private MicroLesson microLesson;

    private Integer progressPercent;

    private String status; // IN_PROGRESS, COMPLETED

    private BigDecimal score;

    private LocalDateTime lastAccessedAt;

    @PrePersist
    public void prePersist() {
        this.lastAccessedAt = LocalDateTime.now();
    }
}
