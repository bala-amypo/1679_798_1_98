package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    // POST /progress/{lessonId}
    @PostMapping("/{lessonId}")
    public ResponseEntity<Progress> recordProgress(
            @PathVariable Long lessonId,
            @RequestBody Progress progress) {
        return ResponseEntity.ok(progressService.saveOrUpdate(lessonId, progress));
    }

    // GET /progress/lesson/{lessonId}
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<Progress> getLessonProgress(
            @PathVariable Long lessonId) {
        return ResponseEntity.ok(progressService.getProgressForLesson(lessonId));
    }

    // GET /progress/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getUserProgress(
            @PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getUserProgress(userId));
    }
}
