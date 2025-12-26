package com.example.demo.controller;

import com.example.demo.entity.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.web.bind.annotation.*;

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
}
