package com.example.demo.controller;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    // POST /recommendations/generate?userId=1
    @PostMapping("/generate")
    public Recommendation generate(
            @RequestParam Long userId,
            @RequestBody RecommendationRequest request
    ) {
        return recommendationService.generateRecommendation(userId, request);
    }

    // GET /recommendations/latest?userId=1
    @GetMapping("/latest")
    public Recommendation latest(@RequestParam Long userId) {
        return recommendationService.getLatestRecommendation(userId);
    }

    // GET /recommendations/user/{userId}?from=2025-01-01&to=2025-12-31
    @GetMapping("/user/{userId}")
    public List<Recommendation> list(
            @PathVariable Long userId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return recommendationService.getRecommendations(userId, from, to);
    }
}
