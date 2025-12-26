package com.example.demo.service.impl;

import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository repository;
    private final UserRepository userRepository;

    public RecommendationServiceImpl(RecommendationRepository repository,
                                     UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<Recommendation> list = repository.findByUser(user);
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }
}
