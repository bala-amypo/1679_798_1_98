// package com.example.demo.controller;

// import com.example.demo.model.Progress;
// import com.example.demo.service.ProgressService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/progress")
// public class ProgressController {

//     @Autowired
//     private ProgressService progressService;

//     // POST or PUT progress
//     @PostMapping
//     public Progress saveProgress(@RequestBody Progress progress) {
//         return progressService.saveProgress(progress);
//     }

//     // GET progress by MicroLesson ID
//     @GetMapping("/lesson/{id}")
//     public Progress getProgressByLesson(@PathVariable("id") Long lessonId) {
//         return progressService.getProgressByMicroLessonId(lessonId);
//     }

//     // GET progress by User ID
//     @GetMapping("/user/{id}")
//     public List<Progress> getProgressByUser(@PathVariable("id") Long userId) {
//         return progressService.getUserProgress(userId);
//     }
// }


package com.example.demo.controller;

import com.example.demo.entity.Progress;
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