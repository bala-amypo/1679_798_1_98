package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate start, LocalDate end) {
        return recommendationRepository.findByUserIdAndDateBetween(userId, start, end);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        return recommendationRepository.findTopByUserIdOrderByGeneratedAtDesc(userId);
    }

    @Override
    public Recommendation generateRecommendation(long userId, RecommendationRequest req) {
        Recommendation recommendation = Recommendation.builder()
                .userId(userId)
                .tags(req.getTags())
                .difficulty(req.getDifficulty())
                .contentType(req.getContentType())
                .generatedAt(LocalDate.now())
                .build();
        return recommendationRepository.save(recommendation);
    }
}
