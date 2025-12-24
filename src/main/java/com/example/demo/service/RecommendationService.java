package com.example.demo.service;

import com.example.demo.entity.Recommendation;
import com.example.demo.dto.RecommendationRequest;

import java.util.List;

public interface RecommendationService {

    Recommendation generateRecommendation(RecommendationRequest request);

    List<Recommendation> getUserRecommendations(Long userId);
}
