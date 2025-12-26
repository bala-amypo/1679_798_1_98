package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.example.demo.entity.Lesson;
import com.example.demo.service.LessonService;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // POST /lessons/course/{courseId}
    @PostMapping("/course/{courseId}")
    public ResponseEntity<Lesson> addLesson(
            @PathVariable Long courseId,
            @RequestBody Lesson lesson) {
        return ResponseEntity.ok(lessonService.addLesson(courseId, lesson));
    }

    // PUT /lessons/{lessonId}
    @PutMapping("/{lessonId}")
    public ResponseEntity<Lesson> updateLesson(
            @PathVariable Long lessonId,
            @RequestBody Lesson lesson) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonId, lesson));
    }

    // GET /lessons/search
    @GetMapping("/search")
    public ResponseEntity<List<Lesson>> searchLessons(
            @RequestParam String query) {
        return ResponseEntity.ok(lessonService.searchLessons(query));
    }

    // GET /lessons/{lessonId}
    @GetMapping("/{lessonId}")
    public ResponseEntity<Lesson> getLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.getLessonById(lessonId));
    }
}
