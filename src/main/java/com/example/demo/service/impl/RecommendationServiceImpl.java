package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Recommendation;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository,
                                     UserRepository userRepository,
                                     MicroLessonRepository microLessonRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.microLessonRepository = microLessonRepository;
    }

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest req) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<MicroLesson> lessons = microLessonRepository.findByFilters(
                req.getTags(),
                req.getDifficulty(),
                req.getContentType()
        );

        String ids = lessons.stream()
                .map(l -> String.valueOf(l.getId()))
                .collect(Collectors.joining(","));

        Recommendation recommendation = Recommendation.builder()
                .recommendedLessonIds(ids)
                .basisSnapshot("Generated")
                .confidenceScore(BigDecimal.valueOf(0.8))
                .build();

        return recommendationRepository.save(recommendation);
    }
}
