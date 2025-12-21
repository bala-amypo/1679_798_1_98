package com.example.demo.service;

import com.example.demo.entity.Recommendation;
import com.example.demo.dto.RecommendationRequest;

import java.time.LocalDate;
import java.util.List;

public interface RecommendationService {

    Recommendation generateRecommendation(Long userId, RecommendationRequest request);

    Recommendation getLatestRecommendation(Long userId);

    List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to);
}
