// package com.example.demo.controller;

// import org.springframework.web.bind.annotation.*;
// import org.springframework.http.ResponseEntity;

// import java.util.List;

// import com.example.demo.model.Recommendation;
// import com.example.demo.service.RecommendationService;

// @RestController
// @RequestMapping("/recommendations")
// public class RecommendationController {

//     private final RecommendationService recommendationService;

//     public RecommendationController(RecommendationService recommendationService) {
//         this.recommendationService = recommendationService;
//     }

//     // POST /recommendations/generate
//     @PostMapping("/generate")
//     public ResponseEntity<Recommendation> generateRecommendation(
//             @RequestParam Long userId) {
//         return ResponseEntity.ok(recommendationService.generate(userId));
//     }

//     // GET /recommendations/latest
//     @GetMapping("/latest")
//     public ResponseEntity<Recommendation> getLatestRecommendation(
//             @RequestParam Long userId) {
//         return ResponseEntity.ok(recommendationService.getLatest(userId));
//     }

//     // GET /recommendations/user/{userId}
//     @GetMapping("/user/{userId}")
//     public ResponseEntity<List<Recommendation>> getUserRecommendations(
//             @PathVariable Long userId) {
//         return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
//     }
// }


package com.example.demo.controller;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.entity.Recommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    
    private final RecommendationService recommendationService;
    
    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendation(
            @RequestParam Long userId,
            @RequestBody RecommendationRequest request) {
        Recommendation recommendation = recommendationService.generateRecommendation(userId, request);
        return ResponseEntity.ok(recommendation);
    }
    
    @GetMapping("/latest")
    public ResponseEntity<Recommendation> getLatestRecommendation(@RequestParam Long userId) {
        Recommendation recommendation = recommendationService.getLatestRecommendation(userId);
        return ResponseEntity.ok(recommendation);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getRecommendations(
            @PathVariable Long userId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        List<Recommendation> recommendations = recommendationService.getRecommendations(userId, from, to);
        return ResponseEntity.ok(recommendations);
    }
}