package com.example.demo.entity;

import lombok.Builder;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Builder
@Entity
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private String title;
    private Integer durationMinutes;
    private String contentType;
    private String difficulty;
    private String tags;
    private LocalDate publishDate;

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public LocalDate getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }

 
    public MicroLesson() { }

    public MicroLesson(Long id, Course course, String title, Integer durationMinutes, String contentType,
                       String difficulty, String tags, LocalDate publishDate) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.contentType = contentType;
        this.difficulty = difficulty;
        this.tags = tags;
        this.publishDate = publishDate;
    }

    // PrePersist method
    @PrePersist
    private void prePersist() {
        if (publishDate == null) {
            publishDate = LocalDate.now();
        }
        if (durationMinutes != null && durationMinutes <= 0) {
            throw new IllegalStateException("durationMinutes must be > 0");
        }
        if (contentType != null && !contentType.equals("VIDEO") 
                && !contentType.equals("ARTICLE") && !contentType.equals("QUIZ")) {
            throw new IllegalStateException("Invalid contentType");
        }
        if (difficulty != null && !difficulty.equals("EASY") 
                && !difficulty.equals("MEDIUM") && !difficulty.equals("HARD")) {
            throw new IllegalStateException("Invalid difficulty");
        }
    }
}
