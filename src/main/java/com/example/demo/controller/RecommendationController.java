package com.example.demo.controller;

import com.example.demo.entity.Recommendation;
import com.example.demo.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public Recommendation getRecommendation(@PathVariable Long id) {
        return service.getRecommendationById(id);
    }

    @GetMapping("/all/{userId}")
    public List<Recommendation> getAllRecommendations(@PathVariable Long userId) {
        return service.getAllRecommendations(userId);
    }

    @PutMapping("/{id}")
    public Recommendation updateRecommendation(@PathVariable Long id, @RequestBody Recommendation r) {
        return service.updateRecommendation(id, r);
    }
}
