package com.example.demo.service;

import com.example.demo.model.Recommendation;

import java.time.LocalDate;
import java.util.List;

public interface RecommendationService {
    Recommendation save(Recommendation recommendation);
    Recommendation getRecommendationsForUser(Long userId);
    Recommendation generateRecommendation(Long userId, Object params);
    Recommendation getLatestRecommendation(Long userId);
    List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to);
}
