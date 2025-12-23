package com.example.demo.dto;

import java.util.List;

public class RecommendationRequest {

    private List<String> lessonIds;
    private String basisSnapshot;
    private Double confidenceScore;

    // Getters and setters
    public List<String> getLessonIds() { return lessonIds; }
    public void setLessonIds(List<String> lessonIds) { this.lessonIds = lessonIds; }

    public String getBasisSnapshot() { return basisSnapshot; }
    public void setBasisSnapshot(String basisSnapshot) { this.basisSnapshot = basisSnapshot; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
}
