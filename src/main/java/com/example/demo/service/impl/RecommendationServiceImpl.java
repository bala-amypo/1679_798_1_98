package com.example.demo.service.impl;

import com.example.demo.model.Recommendation;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RecommendationServiceImpl {
    private final RecommendationRepository repo;
    private final UserRepository userRepo;

    public RecommendationServiceImpl(RecommendationRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> list = repo.findByUserIdOrderByGeneratedAtDesc(userId);
        if (list.isEmpty()) throw new RuntimeException("No recommendation");
        return list.get(0);
    }
}
