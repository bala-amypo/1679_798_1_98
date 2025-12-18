package com.example.demo.repository;

import com.example.demo.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    // Required by automated test
    List<Recommendation> findByUserIdAndGeneratedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
    Optional<Recommendation> findByUserIdOrderByGeneratedAtDesc(Long userId);
}
