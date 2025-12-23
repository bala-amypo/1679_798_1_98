package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contentType;
    private String difficulty;
    private String tag;
    private int durationMinutes;

    private Long courseId; // Add this

    // Lombok @Data generates getter/setter for courseId
}
