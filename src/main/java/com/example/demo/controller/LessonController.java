package com.example.demo.controller;

import com.example.demo.entity.Microlesson;
import com.example.demo.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lessons", description = "Micro-lesson APIs")
@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Operation(summary = "Add lesson to a course")
    @PostMapping("/course/{courseId}")
    public Microlesson addLesson(@PathVariable Long courseId, @RequestBody Microlesson lesson) {
        return lessonService.addLesson(courseId, lesson);
    }

    @Operation(summary = "Update a lesson")
    @PutMapping("/{lessonId}")
    public Microlesson updateLesson(@PathVariable Long lessonId, @RequestBody Microlesson lesson) {
        return lessonService.updateLesson(lessonId, lesson);
    }

    @Operation(summary = "Search lessons with filters")
    @GetMapping("/search")
    public List<Microlesson> searchLessons(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String contentType) {
        return lessonService.findLessonsByFilters(tags, difficulty, contentType);
    }

    @Operation(summary = "Get lesson details")
    @GetMapping("/{lessonId}")
    public Microlesson getLesson(@PathVariable Long lessonId) {
        return lessonService.getLesson(lessonId);
    }
}
