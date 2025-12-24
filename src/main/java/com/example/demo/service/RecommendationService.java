package com.example.demo.service;

import com.example.demo.entity.Recommendation;

public interface RecommendationService {
    Recommendation getLatestRecommendation(Long userId);
}
