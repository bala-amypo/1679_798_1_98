package com.example.demo.repository;

import com.example.demo.entity.MicroLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MicroLessonRepository extends JpaRepository<MicroLesson, Long> {
    // Custom query method instead of findByFilters
    List<MicroLesson> findByTagsContainingAndDifficultyAndContentType(String tags, String difficulty, String contentType);
}
