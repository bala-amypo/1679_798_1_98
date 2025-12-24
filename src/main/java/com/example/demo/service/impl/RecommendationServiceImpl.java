package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.entity.Recommendation;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Recommendation generateRecommendation(RecommendationRequest request) {
        Recommendation recommendation = new Recommendation();
        recommendation.setUserId(request.getUserId());
        recommendation.setMicroLessonId(request.getMicroLessonId());
        recommendation.setReason("Based on your learning progress");

        return recommendationRepository.save(recommendation);
    }

    @Override
    public List<Recommendation> getUserRecommendations(Long userId) {
        return recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
    }
}
