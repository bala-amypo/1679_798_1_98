package com.example.demo.controller;

import com.example.demo.entity.Progress;
import com.example.demo.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Progress", description = "User progress tracking APIs")
@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @Operation(summary = "Record or update lesson progress")
    @PostMapping("/{lessonId}")
    public Progress recordProgress(
            @RequestParam Long userId,
            @PathVariable Long lessonId,
            @RequestBody Progress progress) {
        return progressService.recordProgress(userId, lessonId, progress);
    }

    @Operation(summary = "Get user's progress for a lesson")
    @GetMapping("/lesson/{lessonId}")
    public Progress getLessonProgress(
            @RequestParam Long userId,
            @PathVariable Long lessonId) {
        return progressService.getProgress(userId, lessonId);
    }

    @Operation(summary = "Get all progress for a user")
    @GetMapping("/user/{userId}")
    public List<Progress> getUserProgress(@PathVariable Long userId) {
        return progressService.getUserProgress(userId);
    }
}
