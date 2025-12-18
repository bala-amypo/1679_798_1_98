package com.example.demo.service.impl;
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
            UserRepository userRepository
    ) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recommendation generateRecommendation(Long userId, Recommendation recommendation) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        recommendation.setUser(user);
        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        // ✅ MUST use exact repository method
        return recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
        // ✅ MUST use exact repository method
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        return recommendationRepository.findByUserIdAndGeneratedAtBetween(
                userId,
                start,
                end
        );
    }
}
