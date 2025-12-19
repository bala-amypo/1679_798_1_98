package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "micro_lessons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "content_type", nullable = false)
    private String contentType; // VIDEO, ARTICLE, QUIZ, INTERACTIVE

    @Column(nullable = false)
    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(length = 500)
    private String tags;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @OneToMany(mappedBy = "microLesson", cascade = CascadeType.ALL)
    private List<Progress> progressList;
}
