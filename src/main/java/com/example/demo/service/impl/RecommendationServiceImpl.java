package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.entity.Recommendation;
import com.example.demo.entity.User;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    public RecommendationServiceImpl(
            RecommendationRepository recommendationRepository,
            UserRepository userRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Recommendation recommendation = new Recommendation();
        recommendation.setUser(user);
        recommendation.setRecommendedLessonIds("1,2,3");
        recommendation.setBasisSnapshot("AUTO_GENERATED");
        recommendation.setConfidenceScore(request.getConfidenceScore());

        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        return recommendationRepository
                .findByUserIdOrderByGeneratedAtDesc(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No recommendations found"));
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {

        LocalDateTime start = from != null ? from.atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime end = to != null ? to.atTime(23, 59, 59) : LocalDateTime.now();

        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
    }
}
