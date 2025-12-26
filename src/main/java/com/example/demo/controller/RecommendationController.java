package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.example.demo.model.Recommendation;
import com.example.demo.service.RecommendationService;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    // POST /recommendations/generate
    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendation(
            @RequestParam Long userId) {
        return ResponseEntity.ok(recommendationService.generate(userId));
    }

    // GET /recommendations/latest
    @GetMapping("/latest")
    public ResponseEntity<Recommendation> getLatestRecommendation(
            @RequestParam Long userId) {
        return ResponseEntity.ok(recommendationService.getLatest(userId));
    }

    // GET /recommendations/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(
            @PathVariable Long userId) {
        return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
    }
}
