package com.example.demo.dto;
public class RecommendationRequest {
    private String tags;
    private String difficulty;
    private String contentType;
    private Integer limit;
    private String preferredLearningStyle;

    public RecommendationRequest() {}

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getLimit() {
        return limit;
    }
    
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getPreferredLearningStyle() {
        return preferredLearningStyle;
    }
    
    public void setPreferredLearningStyle(String preferredLearningStyle) {
        this.preferredLearningStyle = preferredLearningStyle;
    }
}
