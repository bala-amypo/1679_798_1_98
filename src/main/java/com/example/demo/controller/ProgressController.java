package com.example.demo.controller;

import com.example.demo.entity.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("/{lessonId}")
    public Progress recordProgress(@PathVariable Long lessonId,
                                   @RequestParam Long userId,
                                   @RequestBody Progress progress) {
        return progressService.recordProgress(userId, lessonId, progress);
    }

    @GetMapping("/lesson/{lessonId}")
    public Progress getLessonProgress(@PathVariable Long lessonId,
                                      @RequestParam Long userId) {
        return progressService.getProgress(userId, lessonId);
    }

    @GetMapping("/user/{userId}")
    public List<Progress> getUserProgress(@PathVariable Long userId) {
        return progressService.getUserProgress(userId);
    }
}
