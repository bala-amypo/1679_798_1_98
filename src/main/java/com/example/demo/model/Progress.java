package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Progress {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private MicroLesson microLesson;

    private Integer progressPercent;
    private String status;
    private BigDecimal score;

    private LocalDateTime lastAccessedAt;

    @PrePersist
    public void prePersist() {
        lastAccessedAt = LocalDateTime.now();
    }
}
