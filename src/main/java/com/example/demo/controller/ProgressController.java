
package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/progress")
@Tag(name = "Progress Tracking")
@RequiredArgsConstructor
public class ProgressController {
    
    private final ProgressService progressService;
    
    @PostMapping("/{lessonId}")
    public ResponseEntity<Progress> recordProgress(
            @PathVariable Long lessonId,
            @RequestParam Long userId,
            @RequestBody Progress progress) {
        Progress recorded = progressService.recordProgress(userId, lessonId, progress);
        return ResponseEntity.ok(recorded);
    }
    
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<Progress> getProgress(
            @PathVariable Long lessonId,
            @RequestParam Long userId) {
        Progress progress = progressService.getProgress(userId, lessonId);
        return ResponseEntity.ok(progress);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getUserProgress(@PathVariable Long userId) {
        List<Progress> progressList = progressService.getUserProgress(userId);
        return ResponseEntity.ok(progressList);
    }
}