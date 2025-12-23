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
    public Recommendation generateRecommendation(long userId, RecommendationRequest req) {
        String tags = req.getTags() != null ? String.join(",", req.getTags()) : "";

        Recommendation recommendation = Recommendation.builder()
                .userId(userId)
                .tags(tags)
                .difficulty(req.getDifficulty())
                .contentType(req.getContentType())
                .generatedAt(LocalDate.now())
                .build();

        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        return recommendationRepository.findTopByUserIdOrderByGeneratedAtDesc(userId);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate start, LocalDate end) {
        if (start == null) start = LocalDate.now().minusDays(30);
        if (end == null) end = LocalDate.now();
        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
    }
}
