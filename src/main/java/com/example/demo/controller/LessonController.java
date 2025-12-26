package com.example.demo.controller;
import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // POST /lessons
    @PostMapping
    public ResponseEntity<MicroLesson> createLesson(@RequestBody MicroLesson lesson) {
        return ResponseEntity.ok(lessonService.createLesson(lesson));
    }

    // PUT /lessons/{lessonId}
    @PutMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> updateLesson(
            @PathVariable Long lessonId,
            @RequestBody MicroLesson lesson) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonId, lesson));
    }

    // GET /lessons/course/{courseId}
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<MicroLesson>> getLessonsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourse(courseId));
    }

    // GET /lessons/{lessonId}
    @GetMapping("/{lessonId}")
    public ResponseEntity<MicroLesson> getLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.getLessonById(lessonId));
    }
}
