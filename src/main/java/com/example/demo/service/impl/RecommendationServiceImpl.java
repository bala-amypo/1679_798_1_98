package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import com.example.demo.util.RecommendationEngine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository lessonRepository;
    private final RecommendationEngine recommendationEngine;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository,
                                     UserRepository userRepository,
                                     MicroLessonRepository lessonRepository,
                                     RecommendationEngine recommendationEngine) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.recommendationEngine = recommendationEngine;
    }

    // ---------------- GENERATE RECOMMENDATION ----------------
    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Fetch all lessons
        List<MicroLesson> allLessons = lessonRepository.findAll();

        // Filter out completed lessons
        Set<Long> completedLessonIds = user.getProgressList() != null
                ? user.getProgressList().stream()
                .filter(p -> "COMPLETED".equalsIgnoreCase(p.getStatus()))
                .map(p -> p.getMicroLesson().getId())
                .collect(Collectors.toSet())
                : Collections.emptySet();

        List<MicroLesson> candidates = allLessons.stream()
                .filter(lesson -> !completedLessonIds.contains(lesson.getId()))
                .collect(Collectors.toList());

        // Use RecommendationEngine to score and rank lessons
        List<MicroLesson> topLessons = recommendationEngine.rankLessons(user, candidates, request);

        // Build recommendedLessonIds
        String recommendedLessonIds = topLessons.stream()
                .map(lesson -> lesson.getId().toString())
                .collect(Collectors.joining(","));

        // Generate basis snapshot as JSON (simple Map)
        Map<String, Object> snapshot = recommendationEngine.createBasisSnapshot(user, topLessons, request);
        String basisSnapshot = recommendationEngine.convertToJson(snapshot);

        // Compute confidence score
        BigDecimal confidenceScore = recommendationEngine.calculateConfidence(user, topLessons);

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(recommendedLessonIds)
                .basisSnapshot(basisSnapshot)
                .confidenceScore(confidenceScore)
                .generatedAt(LocalDateTime.now())
                .build();

        return recommendationRepository.save(recommendation);
    }

    // ---------------- GET LATEST RECOMMENDATION ----------------
    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> list = recommendationRepository
                .findByUserIdOrderByGeneratedAtDesc(userId);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found for user");
        }
        return list.get(0);
    }

    // ---------------- GET RECOMMENDATIONS BY DATE RANGE ----------------
    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
        LocalDateTime start = from != null ? from.atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime end = to != null ? to.atTime(23, 59, 59) : LocalDateTime.MAX;

        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
    }
}
