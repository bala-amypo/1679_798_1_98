package com.example.demo.controller;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@Tag(name = "LessonController", description = "Handles micro-lesson creation, retrieval, search, and updates")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Operation(summary = "Add a lesson to a course")
    @PostMapping("/course/{courseId}")
    public ResponseEntity<MicroLesson> addLesson(
            @PathVariable Long courseId,
            @RequestBody MicroLesson lesson) {
        return ResponseEntity.ok(lessonService.addLesson(courseId, lesson));
    }

    @Operation(summary = "Get a lesson by ID")
    @GetMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> getLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.getLesson(lessonId));
    }

    @Operation(summary = "Search lessons by tags, difficulty, and content type")
    @GetMapping("/search")
    public ResponseEntity<List<MicroLesson>> searchLessons(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(lessonService.findLessonsByFilters(tags, difficulty, contentType));
    }

    @Operation(summary = "Update an existing lesson")
    @PutMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> updateLesson(
            @PathVariable Long lessonId,
            @RequestBody MicroLesson lesson) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonId, lesson));
    }
}
