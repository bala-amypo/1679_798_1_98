// src/main/java/com/example/demo/controller/ProgressController.java
package com.example.demo.controller;

import com.example.demo.entity.Progress;
import com.example.demo.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Record or update progress for a lesson")
    public ResponseEntity<Progress> recordProgress(@PathVariable Long lessonId,
                                                   @RequestParam Long userId,
                                                   @RequestBody Progress progress) {
        Progress updated = progressService.recordProgress(userId, lessonId, progress);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/lesson/{lessonId}")
    @Operation(summary = "Get progress for a lesson for a user")
    public ResponseEntity<Progress> getProgress(@RequestParam Long userId,
                                                @PathVariable Long lessonId) {
        return ResponseEntity.ok(progressService.getProgress(userId, lessonId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all progress records for a user")
    public ResponseEntity<List<Progress>> getUserProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getUserProgress(userId));
    }
}
