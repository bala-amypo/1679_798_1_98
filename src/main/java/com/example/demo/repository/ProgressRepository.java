package com.example.demo.repository;

import com.example.demo.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    // Required by automated test
    Optional<Progress> findByUserIdAndMicroLessonId(Long userId, Long lessonId);
    List<Progress> findByUserIdOrderByLastAccessedAtDesc(Long userId);
}
