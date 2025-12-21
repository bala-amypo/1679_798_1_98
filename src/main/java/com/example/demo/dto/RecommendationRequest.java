package com.example.demo.dto;

import java.math.BigDecimal;

public class RecommendationRequest {

    private BigDecimal confidenceScore;

    public RecommendationRequest() {
    }

    public BigDecimal getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(BigDecimal confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
}
