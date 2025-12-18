package com.example.demo.controller;

import com.example.demo.entity.MicroLesson;
import com.example.demo.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/course/{courseId}")
    public MicroLesson addLesson(@PathVariable Long courseId, @RequestBody MicroLesson lesson) {
        return lessonService.addLesson(courseId, lesson);
    }

    @PutMapping("/{lessonId}")
    public MicroLesson updateLesson(@PathVariable Long lessonId, @RequestBody MicroLesson lesson) {
        return lessonService.updateLesson(lessonId, lesson);
    }

    @GetMapping("/search")
    public List<MicroLesson> searchLessons(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String contentType) {
        return lessonService.findLessonsByFilters(tags, difficulty, contentType);
    }

    @GetMapping("/{lessonId}")
    public MicroLesson getLesson(@PathVariable Long lessonId) {
        return lessonService.getLesson(lessonId);
    }
}
