package com.example.demo.controller;
import com.example.demo.entity.Recommendation;
import com.example.demo.dto.RecommendationRequest;
import com.example.demo.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @PostMapping("/generate")
    public Recommendation generate(@RequestParam Long userId, @RequestBody RecommendationRequest request) {
        return recommendationService.generateRecommendation(userId, request);
    }

    @GetMapping("/latest")
    public Recommendation getLatest(@RequestParam Long userId) {
        return recommendationService.getLatestRecommendation(userId);
    }

    @GetMapping("/user/{userId}")
    public List<Recommendation> getRecommendations(@PathVariable Long userId,
                                                   @RequestParam(required = false) String from,
                                                   @RequestParam(required = false) String to) {
        LocalDate fromDate = from != null ? LocalDate.parse(from) : LocalDate.MIN;
        LocalDate toDate = to != null ? LocalDate.parse(to) : LocalDate.MAX;
        return recommendationService.getRecommendations(userId, fromDate, toDate);
    }
}
