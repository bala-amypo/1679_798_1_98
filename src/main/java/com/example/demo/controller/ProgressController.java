package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@Tag(name = "Progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    // POST /progress/{lessonId}?userId=1
    @PostMapping("/{lessonId}")
    public Progress recordProgress(
            @PathVariable Long lessonId,
            @RequestParam Long userId,
            @RequestBody Progress progress
    ) {
        return progressService.recordProgress(userId, lessonId, progress);
    }

    // GET /progress/lesson/{lessonId}?userId=1
    @GetMapping("/lesson/{lessonId}")
    public Progress getProgress(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        return progressService.getProgress(userId, lessonId);
    }

    // GET /progress/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Progress> getUserProgress(@PathVariable Long userId) {
        return progressService.getUserProgress(userId);
    }
}
