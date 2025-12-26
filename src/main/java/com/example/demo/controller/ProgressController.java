package com.example.demo.controller;

import com.example.demo.entity.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService service;

    public ProgressController(ProgressService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/{lessonId}")
    public Progress record(@PathVariable Long userId,
                           @PathVariable Long lessonId,
                           @RequestBody Progress p) {
        return service.recordProgress(userId, lessonId, p);
    }

    @GetMapping("/{userId}/{lessonId}")
    public Progress getProgress(@PathVariable Long userId,
                                @PathVariable Long lessonId) {
        return service.getProgress(userId, lessonId);
    }

    @GetMapping("/all/{userId}")
    public List<Progress> getAllProgress(@PathVariable Long userId) {
        return service.getAllProgress(userId);
    }

    @PutMapping("/{userId}/{lessonId}")
    public Progress updateProgress(@PathVariable Long userId,
                                   @PathVariable Long lessonId,
                                   @RequestBody Progress p) {
        return service.updateProgress(userId, lessonId, p);
    }
}
