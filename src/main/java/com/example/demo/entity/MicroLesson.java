package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MicroLesson {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String contentType;
    private String difficulty;
    private Integer durationMinutes;
    private String tags;

    @ManyToOne
    private Course course;
}
