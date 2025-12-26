package com.example.demo.service.impl;

import com.example.demo.model.Recommendation;
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
    public Recommendation generate(Long userId) {
        Recommendation recommendation = new Recommendation();
        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatest(Long userId) {
        return recommendationRepository
                .findTopByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Recommendation> getUserRecommendations(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }
}
