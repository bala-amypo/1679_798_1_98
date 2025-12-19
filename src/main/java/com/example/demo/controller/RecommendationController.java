package com.example.demo.controller;
import com.example.demo.entity.Recommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Recommendations", description = "Lesson recommendation APIs")
@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Generate recommendation for a user")
    @PostMapping("/generate")
    public Recommendation generate(
            @RequestParam Long userId,
            @RequestBody Recommendation recommendation) {
        return recommendationService.generateRecommendation(userId, recommendation);
    }

    @Operation(summary = "Get latest recommendation")
    @GetMapping("/latest")
    public Recommendation getLatest(@RequestParam Long userId) {
        return recommendationService.getLatestRecommendation(userId);
    }

    @Operation(summary = "Get recommendations by date range")
    @GetMapping("/user/{userId}")
    public List<Recommendation> getByDate(
            @PathVariable Long userId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return recommendationService.getRecommendations(userId, from, to);
    }
}
