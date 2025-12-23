package com.example.demo.controller;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "RecommendationController", description = "Handles lesson recommendation operations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Generate a recommendation for a user")
    @PostMapping("/generate/{userId}")
    public ResponseEntity<Recommendation> generateRecommendation(
            @PathVariable Long userId,
            @RequestBody RecommendationRequest request) {
        Recommendation recommendation = recommendationService.generateRecommendation(userId, request);
        return ResponseEntity.ok(recommendation);
    }

    @Operation(summary = "Get the latest recommendation for a user")
    @GetMapping("/latest/{userId}")
    public ResponseEntity<Recommendation> getLatestRecommendation(@PathVariable Long userId) {
        Recommendation latest = recommendationService.getLatestRecommendation(userId);
        return ResponseEntity.ok(latest);
    }

    @Operation(summary = "List recommendations for a user with optional date filters")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> listRecommendations(
            @PathVariable Long userId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        List<Recommendation> recommendations = recommendationService.getRecommendations(userId, from, to);
        return ResponseEntity.ok(recommendations);
    }
}
