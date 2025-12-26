package com.example.demo.repository;

import com.example.demo.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    // Find progress by MicroLesson ID
    Progress findByMicroLesson_Id(Long microLessonId);

    // Find all progress for a specific User ID
    List<Progress> findByUser_Id(Long userId);
}
