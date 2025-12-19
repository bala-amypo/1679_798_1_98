package com.example.demo.repository;

import com.example.demo.entity.MicroLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MicroLessonRepository extends JpaRepository<MicroLesson, Long> {

    // Exact method name required by test cases
    default List<MicroLesson> findByFilters(String tags, String difficulty, String contentType) {
        return findByTagsContainingAndDifficultyAndContentType(tags, difficulty, contentType);
    }

    // Standard Spring Data JPA method used internally
    List<MicroLesson> findByTagsContainingAndDifficultyAndContentType(String tags, String difficulty, String contentType);
}
