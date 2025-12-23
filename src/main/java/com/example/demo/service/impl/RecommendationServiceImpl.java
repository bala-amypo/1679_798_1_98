package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Recommendation;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Recommendation generateRecommendation(long userId, RecommendationRequest request) {
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
                .orElseThrow(() -> new ResourceNotFoundException("No recommendation found for user: " + userId));
    }

    @Override
    public List<Recommendation> getRecommendationsForUser(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }
}
