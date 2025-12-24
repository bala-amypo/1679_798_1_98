package com.example.demo.service.impl;

import com.example.demo.model.Recommendation;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;

public class RecommendationServiceImpl {

    private final RecommendationRepository repo;
    private final UserRepository userRepo;
    private final Object lessonRepo;

    public RecommendationServiceImpl(RecommendationRepository repo, UserRepository userRepo, Object lessonRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.lessonRepo = lessonRepo;
    }

    public Recommendation getLatestRecommendation(Long userId) {
        return repo.findByUserIdOrderByGeneratedAtDesc(userId)
                .stream().findFirst().orElseThrow();
    }
}
