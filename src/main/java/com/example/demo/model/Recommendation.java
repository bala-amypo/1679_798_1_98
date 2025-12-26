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
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 2000)
    private String recommendedLessonIds;

    @Column(length = 1000)
    private String reason;

    private BigDecimal confidenceScore;

    private LocalDateTime generatedAt;

    @PrePersist
    public void prePersist() {
        this.generatedAt = LocalDateTime.now();
    }
}
