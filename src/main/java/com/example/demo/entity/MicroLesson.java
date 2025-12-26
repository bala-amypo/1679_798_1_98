


package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @NotBlank(message = "Title is required")
    @Size(max = 150)
    @Column(nullable = false)
    private String title;
    
    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    @Max(value = 15, message = "Duration cannot exceed 15 minutes for micro-learning")
    @Column(nullable = false)
    private Integer durationMinutes;
    
    @NotBlank(message = "Content type is required")
    @Size(max = 50)
    @Column(nullable = false)
    private String contentType; // VIDEO, ARTICLE, QUIZ, INTERACTIVE
    
    @NotBlank(message = "Difficulty is required")
    @Size(max = 50)
    @Column(nullable = false)
    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED
    
    @Size(max = 500)
    private String tags;
    
    @NotNull(message = "Publish date is required")
    private LocalDate publishDate;
}