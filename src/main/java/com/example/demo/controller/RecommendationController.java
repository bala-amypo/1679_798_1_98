package com.example.demo.controller;

import com.example.demo.entity.Recommendation;
import com.example.demo.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping("/{userId}/latest")
    public Recommendation latest(@PathVariable Long userId) {
        return service.getLatestRecommendation(userId);
    }
}
