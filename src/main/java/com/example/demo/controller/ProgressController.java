package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    // POST or PUT progress
    @PostMapping
    public Progress saveProgress(@RequestBody Progress progress) {
        return progressService.saveProgress(progress);
    }

    // GET progress by MicroLesson ID
    @GetMapping("/lesson/{id}")
    public Progress getProgressByLesson(@PathVariable("id") Long lessonId) {
        return progressService.getProgressByMicroLessonId(lessonId);
    }

    // GET progress by User ID
    @GetMapping("/user/{id}")
    public List<Progress> getProgressByUser(@PathVariable("id") Long userId) {
        return progressService.getUserProgress(userId);
    }
}
