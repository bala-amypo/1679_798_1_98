package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {

    // Lesson fields
    private String title;
    private Integer durationMinutes; // integer, optional
    private String contentType;
    private String difficulty;
    private String tags; // comma-separated string
    private LocalDate publishDate;


}
