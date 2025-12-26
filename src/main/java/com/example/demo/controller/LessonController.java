package com.example.demo.controller;

import com.example.demo.entity.MicroLesson;
import com.example.demo.service.LessonService;
import org.springframework.web.bind.annotation.*;

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
}
