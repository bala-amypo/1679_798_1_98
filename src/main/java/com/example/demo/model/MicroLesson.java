package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicroLesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String difficulty;
    private String contentType;
    private Integer durationMinutes;
    private String tags;

    @ManyToOne
    private Course course;
}
