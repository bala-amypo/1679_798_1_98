package com.example.demo.controller;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@Tag(name = "Lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // POST /lessons/course/{courseId}
    @PostMapping("/course/{courseId}")
    public MicroLesson addLesson(
            @PathVariable Long courseId,
            @RequestBody MicroLesson lesson
    ) {
        return lessonService.addLesson(courseId, lesson);
    }

    // PUT /lessons/{lessonId}
    @PutMapping("/{lessonId}")
    public MicroLesson updateLesson(
            @PathVariable Long lessonId,
            @RequestBody MicroLesson lesson
    ) {
        return lessonService.updateLesson(lessonId, lesson);
    }

    // GET /lessons/search
    @GetMapping("/search")
    public List<MicroLesson> searchLessons(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String contentType
    ) {
        return lessonService.findLessonsByFilters(tags, difficulty, contentType);
    }

    // GET /lessons/{lessonId}
    @GetMapping("/{lessonId}")
    public MicroLesson getLesson(@PathVariable Long lessonId) {
        return lessonService.getLesson(lessonId);
    }
}
