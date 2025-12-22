package com.example.demo.controller;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.entity.Recommendation;
import com.example.demo.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/generate")
    public Recommendation generate(@RequestBody RecommendationRequest request) {
        return recommendationService.generateRecommendation(1L, request);
    }

    @GetMapping("/latest")
    public Recommendation latest() {
        return recommendationService.getLatestRecommendation(1L);
    }

    @GetMapping("/user/{userId}")
    public List<Recommendation> list(
            @PathVariable Long userId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        return recommendationService.getRecommendations(userId, from, to);
    }
}
