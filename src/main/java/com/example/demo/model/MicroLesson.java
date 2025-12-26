package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Optional: bidirectional relationship
    @OneToMany(mappedBy = "microLesson")
    private List<Progress> progresses;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Progress> getProgresses() { return progresses; }
    public void setProgresses(List<Progress> progresses) { this.progresses = progresses; }
}
