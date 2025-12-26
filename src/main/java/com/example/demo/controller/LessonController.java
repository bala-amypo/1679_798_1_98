package com.example.demo.controller;

import com.example.demo.entity.MicroLesson;
import com.example.demo.service.LessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @PostMapping("/{courseId}")
    public MicroLesson add(@PathVariable Long courseId,
                           @RequestBody MicroLesson m) {
        return service.addLesson(courseId, m);
    }

    @GetMapping("/{id}")
    public MicroLesson getLesson(@PathVariable Long id) {
        return service.getLessonById(id);
    }

    @GetMapping("/all")
    public List<MicroLesson> getAllLessons() {
        return service.getAllLessons();
    }

    @PutMapping("/{id}")
    public MicroLesson updateLesson(@PathVariable Long id, @RequestBody MicroLesson m) {
        return service.updateLesson(id, m);
    }
}
