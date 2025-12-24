package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer progressPercent;
    private String status;
    private BigDecimal score;
    private LocalDateTime lastAccessedAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private MicroLesson microLesson;

    @PrePersist
    public void prePersist() {
        if (lastAccessedAt == null) lastAccessedAt = LocalDateTime.now();
        if ("COMPLETED".equals(status)) progressPercent = 100;
    }
}
