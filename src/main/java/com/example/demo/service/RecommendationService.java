package com.example.demo.service;

import com.example.demo.entity.Recommendation;
import com.example.demo.entity.User;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    public RecommendationService(RecommendationRepository recommendationRepository, UserRepository userRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
    }

    // GET latest
    public Recommendation getLatestRecommendation(Long userId) {
        return recommendationRepository.findTopByUserIdOrderByCreatedAtDesc(userId).orElse(null);
    }

    // GET by ID
    public Recommendation getRecommendationById(Long id) {
        return recommendationRepository.findById(id).orElse(null);
    }

    // GET all
    public List<Recommendation> getAllRecommendations(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }

    // PUT
    public Recommendation updateRecommendation(Long id, Recommendation recommendation) {
        Recommendation existing = recommendationRepository.findById(id).orElseThrow(() -> new RuntimeException("Recommendation not found"));
        existing.setTitle(recommendation.getTitle());
        existing.setDescription(recommendation.getDescription());
        return recommendationRepository.save(existing);
    }
}
