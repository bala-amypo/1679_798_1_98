package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@Tag(name = "ProgressController", description = "Handles user progress tracking")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @Operation(summary = "Record or update progress for a lesson for a specific user")
    @PostMapping("/{userId}/lesson/{lessonId}")
    public ResponseEntity<Progress> recordProgress(
            @PathVariable Long userId,
            @PathVariable Long lessonId,
            @RequestBody Progress progress) {
        Progress savedProgress = progressService.recordProgress(userId, lessonId, progress);
        return ResponseEntity.ok(savedProgress);
    }

    @Operation(summary = "Get progress of a specific user for a lesson")
    @GetMapping("/{userId}/lesson/{lessonId}")
    public ResponseEntity<Progress> getProgress(
            @PathVariable Long userId,
            @PathVariable Long lessonId) {
        Progress progress = progressService.getProgress(userId, lessonId);
        return ResponseEntity.ok(progress);
    }

    @Operation(summary = "Get all progress records for a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getUserProgress(@PathVariable Long userId) {
        List<Progress> progressList = progressService.getUserProgress(userId);
        return ResponseEntity.ok(progressList);
    }
}
