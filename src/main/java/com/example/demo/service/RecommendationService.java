package com.example.demo.service;

import com.example.demo.model.Recommendation;
import java.time.LocalDate;
import java.util.List;

public interface RecommendationService {
    List<Recommendation> getRecommendations(Long userId, LocalDate start, LocalDate end);
    Recommendation getLatestRecommendation(Long userId); // required
}
