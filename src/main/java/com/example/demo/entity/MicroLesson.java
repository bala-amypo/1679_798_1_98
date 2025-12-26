// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.util.List;

// @Entity
// public class MicroLesson {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String title;
//     private String content; // Added to fix getContent() error

//     @OneToMany(mappedBy = "microLesson")
//     private List<Progress> progresses;

//     // Getters and setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public String getTitle() { return title; }
//     public void setTitle(String title) { this.title = title; }

//     public String getContent() { return content; }
//     public void setContent(String content) { this.content = content; }

//     public List<Progress> getProgresses() { return progresses; }
//     public void setProgresses(List<Progress> progresses) { this.progresses = progresses; }
// }


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