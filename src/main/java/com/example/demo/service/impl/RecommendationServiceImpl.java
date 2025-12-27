package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    private final ProgressRepository progressRepository;

    // ✅ ONLY ONE CONSTRUCTOR — NO NULLS
    public RecommendationServiceImpl(
            RecommendationRepository recommendationRepository,
            UserRepository userRepository,
            MicroLessonRepository microLessonRepository,
            ProgressRepository progressRepository) {

        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.microLessonRepository = microLessonRepository;
        this.progressRepository = progressRepository;
    }

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest params) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Progress> progressList =
                progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);

        String tags = (params.getTags() == null || params.getTags().isEmpty())
                ? null
                : String.join(",", params.getTags());

        List<MicroLesson> candidates = microLessonRepository.findByFilters(
                tags,
                params.getTargetDifficulty(),
                params.getContentType()
        );

        List<Long> completedLessonIds = progressList.stream()
                .filter(p -> "COMPLETED".equals(p.getStatus()))
                .map(p -> p.getMicroLesson().getId())
                .collect(Collectors.toList());

        List<MicroLesson> recommendedLessons = candidates.stream()
                .filter(l -> !completedLessonIds.contains(l.getId()))
                .limit(params.getMaxItems() != null ? params.getMaxItems() : 5)
                .collect(Collectors.toList());

        String recommendedIds = recommendedLessons.stream()
                .map(l -> l.getId().toString())
                .collect(Collectors.joining(","));

        BigDecimal confidenceScore =
                calculateConfidenceScore(recommendedLessons.size(), progressList.size());

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(recommendedIds)
                .confidenceScore(confidenceScore)
                .build();

        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {

        List<Recommendation> list =
                recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found");
        }

        return list.get(0);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        return recommendationRepository
                .findByUserIdAndGeneratedAtBetween(userId, start, end);
    }

    private BigDecimal calculateConfidenceScore(int recommendedCount, int progressCount) {

        double score = Math.min(1.0, recommendedCount / 5.0);
        score += Math.min(0.5, progressCount / 20.0);

        return BigDecimal.valueOf(
                Math.max(0.1, Math.min(1.0, score))
        );
    }
}
