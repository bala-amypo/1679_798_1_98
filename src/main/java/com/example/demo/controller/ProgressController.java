package com.example.demo.controller;
import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public Progress recordProgress(@RequestParam Long userId,
                                   @RequestParam Long lessonId,
                                   @RequestBody Progress progress) {
        return progressService.recordProgress(userId, lessonId, progress);
    }

    @GetMapping("/{userId}")
    public List<Progress> getUserProgress(@PathVariable Long userId) {
        return progressService.getUserProgress(userId);
    }

    @GetMapping("/lesson/{lessonId}")
    public Progress getLessonProgress(@PathVariable Long lessonId,
                                      @RequestParam Long userId) {
        return progressService.getProgress(userId, lessonId);
    }
}
