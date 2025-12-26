package com.example.demo.repository;

import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    Recommendation findFirstByUserOrderByGeneratedAtDesc(User user);

    List<Recommendation> findByUser(User user);

    List<Recommendation> findByUserIdAndGeneratedAtBetween(
        Long userId,
        LocalDateTime start,
        LocalDateTime end
    );

    List<Recommendation> findByUserIdOrderByGeneratedAtDesc(Long userId);
}
