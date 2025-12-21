package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private MicroLesson microLesson;

    private String status;
    private Integer progressPercent;
    private LocalDateTime lastAccessedAt;

    @PrePersist
    void onCreate() {
        lastAccessedAt = LocalDateTime.now();
    }

    public Progress() {}
}
