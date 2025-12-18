package com.example.demo.service.impl;
import com.example.demo.entity.Recommendation;
import com.example.demo.entity.User;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Recommendation generateRecommendation(Long userId, Recommendation recommendation) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        recommendation.setUser(user);
        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        return recommendationRepository.findAll().stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .max((r1, r2) -> r1.getGeneratedAt().compareTo(r2.getGeneratedAt()))
                .orElse(null);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
        return recommendationRepository.findAll().stream()
                .filter(r -> r.getUser().getId().equals(userId) &&
                        !r.getGeneratedAt().toLocalDate().isBefore(from) &&
                        !r.getGeneratedAt().toLocalDate().isAfter(to))
                .toList();
    }
}
