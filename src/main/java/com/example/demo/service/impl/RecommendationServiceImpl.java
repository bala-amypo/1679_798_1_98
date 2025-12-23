package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest request) {
        Recommendation recommendation = new Recommendation();
        recommendation.setUserId(userId);
        recommendation.setGeneratedAt(LocalDateTime.now());
        recommendation.setRecommendedLessonIds(String.join(",", request.getLessonIds()));
        recommendation.setBasisSnapshot(request.getBasisSnapshot());
        recommendation.setConfidenceScore(request.getConfidenceScore());

        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        return recommendationRepository.findTopByUserIdOrderByGeneratedAtDesc(userId)
                .orElse(null);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate startDate, LocalDate endDate) {
        return recommendationRepository.findByUserId(userId).stream()
                .filter(r -> !r.getGeneratedAt().toLocalDate().isBefore(startDate)
                        && !r.getGeneratedAt().toLocalDate().isAfter(endDate))
                .toList();
    }
}
