// package com.example.demo.service;

// import com.example.demo.model.Recommendation;
// import java.util.List;

// public interface RecommendationService {

//     Recommendation generate(Long userId);

//     Recommendation getLatest(Long userId);

//     List<Recommendation> getUserRecommendations(Long userId);
// }


package com.example.demo.service;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.entity.Recommendation;
import java.time.LocalDate;
import java.util.List;

public interface RecommendationService {
    Recommendation generateRecommendation(Long userId, RecommendationRequest params);
    Recommendation getLatestRecommendation(Long userId);
    List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to);
}