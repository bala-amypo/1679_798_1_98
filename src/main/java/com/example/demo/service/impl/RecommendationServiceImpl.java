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

        // Convert List<String> tags to comma-separated string
        String tags = String.join(",", req.getTags());

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
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(7); // example: last 7 days
        List<Recommendation> recs = recommendationRepository.findByUserIdAndDateBetween(userId, startDate, today);
        return recs.isEmpty() ? null : recs.get(recs.size() - 1);
    }
}
