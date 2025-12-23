package com.example.demo.service.impl;
import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
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

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository,
                                     UserRepository userRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
    }

    // ✅ MUST USE Long (NOT long)
    @Override
    public Recommendation generateRecommendation(Long userId,
                                                  RecommendationRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds("1,2,3")
                .confidenceScore(null)
                .basisSnapshot(null)
                .build();

        return recommendationRepository.save(recommendation);
    }

    // ✅ SIGNATURE FIXED
    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        return recommendationRepository
                .findByUserIdOrderByGeneratedAtDesc(userId)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recommendation not found"));
    }

    // ✅ SIGNATURE FIXED
    @Override
    public List<Recommendation> getRecommendations(Long userId,
                                                   LocalDate from,
                                                   LocalDate to) {

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        return recommendationRepository
                .findByUserIdAndGeneratedAtBetween(userId, start, end);
    }
}
