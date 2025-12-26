


package com.example.demo.repository;

import com.example.demo.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByUserIdAndMicroLessonId(Long userId, Long microLessonId);
    List<Progress> findByUserIdOrderByLastAccessedAtDesc(Long userId);
}