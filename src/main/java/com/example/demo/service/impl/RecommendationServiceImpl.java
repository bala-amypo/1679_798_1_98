// package com.example.demo.service.impl;

// import com.example.demo.model.Recommendation;
// import com.example.demo.repository.RecommendationRepository;
// import com.example.demo.service.RecommendationService;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class RecommendationServiceImpl implements RecommendationService {

//     private final RecommendationRepository recommendationRepository;

//     public RecommendationServiceImpl(RecommendationRepository recommendationRepository) {
//         this.recommendationRepository = recommendationRepository;
//     }

//     @Override
//     public Recommendation generate(Long userId) {
//         Recommendation recommendation = new Recommendation();
//         return recommendationRepository.save(recommendation);
//     }

//     @Override
//     public Recommendation getLatest(Long userId) {
//         return recommendationRepository
//                 .findTopByUserIdOrderByCreatedAtDesc(userId);
//     }

//     @Override
//     public List<Recommendation> getUserRecommendations(Long userId) {
//         return recommendationRepository.findByUserId(userId);
//     }
// }


package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    private final ProgressRepository progressRepository;
    
    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest params) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Get user's recent progress
        List<Progress> recentProgress = progressRepository
            .findByUserIdOrderByLastAccessedAtDesc(userId);
        
        // Get completed lesson IDs
        Set<Long> completedLessonIds = recentProgress.stream()
            .filter(p -> "COMPLETED".equals(p.getStatus()))
            .map(p -> p.getMicroLesson().getId())
            .collect(Collectors.toSet());
        
        // Get all available lessons
        List<MicroLesson> allLessons = microLessonRepository.findAll();
        
        // Filter out completed lessons
        List<MicroLesson> candidateLessons = allLessons.stream()
            .filter(lesson -> !completedLessonIds.contains(lesson.getId()))
            .collect(Collectors.toList());
        
        // Apply filters from request
        candidateLessons = candidateLessons.stream()
            .filter(lesson -> params.getTags() == null || 
                    lesson.getTags() == null || 
                    lesson.getTags().contains(params.getTags()))
            .filter(lesson -> params.getDifficulty() == null || 
                    lesson.getDifficulty().equals(params.getDifficulty()))
            .filter(lesson -> params.getContentType() == null || 
                    lesson.getContentType().equals(params.getContentType()))
            .collect(Collectors.toList());
        
        // Score and rank lessons (simplified algorithm)
        List<ScoredLesson> scoredLessons = candidateLessons.stream()
            .map(lesson -> {
                double score = calculateLessonScore(lesson, user, recentProgress, params);
                return new ScoredLesson(lesson, score);
            })
            .sorted((a, b) -> Double.compare(b.score, a.score)) // descending
            .limit(params.getLimit() != null ? params.getLimit() : 5)
            .collect(Collectors.toList());
        
        // Build recommendation
        String recommendedIds = scoredLessons.stream()
            .map(sl -> sl.lesson.getId().toString())
            .collect(Collectors.joining(","));
        
        BigDecimal confidenceScore = scoredLessons.isEmpty() ? 
            BigDecimal.ZERO : 
            BigDecimal.valueOf(scoredLessons.get(0).score);
        
        // Create basis snapshot
        String basisSnapshot = String.format(
            "{\"user_id\":%d,\"preferred_learning_style\":\"%s\",\"tags\":\"%s\",\"difficulty\":\"%s\",\"content_type\":\"%s\"}",
            userId,
            user.getPreferredLearningStyle() != null ? user.getPreferredLearningStyle() : "",
            params.getTags() != null ? params.getTags() : "",
            params.getDifficulty() != null ? params.getDifficulty() : "",
            params.getContentType() != null ? params.getContentType() : ""
        );
        
        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(recommendedIds)
                .confidenceScore(confidenceScore)
                .basisSnapshot(basisSnapshot)
                .build();
        
        return recommendationRepository.save(recommendation);
    }
    
    private double calculateLessonScore(MicroLesson lesson, User user, 
                                       List<Progress> recentProgress, 
                                       RecommendationRequest params) {
        double score = 0.5; // Base score
        
        // Match with user's preferred learning style
        if (user.getPreferredLearningStyle() != null && 
            lesson.getTags() != null && 
            lesson.getTags().contains(user.getPreferredLearningStyle())) {
            score += 0.2;
        }
        
        // Match with request tags
        if (params.getTags() != null && 
            lesson.getTags() != null && 
            lesson.getTags().contains(params.getTags())) {
            score += 0.2;
        }
        
        // Match with request difficulty
        if (params.getDifficulty() != null && 
            lesson.getDifficulty().equals(params.getDifficulty())) {
            score += 0.1;
        }
        
        // Match with request content type
        if (params.getContentType() != null && 
            lesson.getContentType().equals(params.getContentType())) {
            score += 0.1;
        }
        
        // Cap at 1.0
        return Math.min(score, 1.0);
    }
    
    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> recommendations = recommendationRepository
            .findByUserIdOrderByGeneratedAtDesc(userId);
        
        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found for user");
        }
        
        return recommendations.get(0);
    }
    
    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);
        
        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
    }
    
    // Helper class for scoring
    private static class ScoredLesson {
        MicroLesson lesson;
        double score;
        
        ScoredLesson(MicroLesson lesson, double score) {
            this.lesson = lesson;
            this.score = score;
        }
    }
}