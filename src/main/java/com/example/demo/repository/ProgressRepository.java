package com.example.demo.repository;

import com.example.demo.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    Progress findByLessonId(Long lessonId);

    List<Progress> findByUserId(Long userId);
}
