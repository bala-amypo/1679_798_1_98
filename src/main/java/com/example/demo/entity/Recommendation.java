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
    private String recommendedLessonIds; // csv: 1,2,3

    @Column(length = 2000)
    private String basisSnapshot; // json string

    private BigDecimal confidenceScore; // 0.0 – 1.0

    private LocalDateTime generatedAt;

    @PrePersist
    public void prePersist() {
        this.generatedAt = LocalDateTime.now();
    }
}
