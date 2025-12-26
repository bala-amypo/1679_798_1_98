package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contentType; // VIDEO, TEXT

    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED

    private Integer durationMinutes;

    private String tags; // csv: java,spring,jpa

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
