package com.example.demo.controller;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@Tag(name = "Lesson Management")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    // Add lesson to a course
    @PostMapping("/course/{courseId}")
    public ResponseEntity<MicroLesson> addLesson(
            @PathVariable Long courseId,
            @RequestBody RecommendationRequest request) {
        MicroLesson created = lessonService.addLesson(courseId, request);
        return ResponseEntity.ok(created);
    }

    // Update existing lesson
    @PutMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> updateLesson(
            @PathVariable Long lessonId,
            @RequestBody RecommendationRequest request) {

        MicroLesson updated = lessonService.updateLesson(lessonId, request);
        return ResponseEntity.ok(updated); // Lazy relations handled in entity
    }

    // Search lessons with optional filters
    @GetMapping("/search")
    public ResponseEntity<List<MicroLesson>> searchLessons(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(
                lessonService.findLessonsByFilters(tags, difficulty, contentType)
        );
    }

    // Get single lesson by ID
    @GetMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> getLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.getLesson(lessonId));
    }
}
