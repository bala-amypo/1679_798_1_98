package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int percentage;

    @ManyToOne
    @JoinColumn(name = "micro_lesson_id")
    private MicroLesson microLesson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Assuming you have a User entity

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getPercentage() { return percentage; }
    public void setPercentage(int percentage) { this.percentage = percentage; }

    public MicroLesson getMicroLesson() { return microLesson; }
    public void setMicroLesson(MicroLesson microLesson) { this.microLesson = microLesson; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
