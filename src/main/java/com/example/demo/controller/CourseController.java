package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping("/{instructorId}")
    public Course create(@PathVariable Long instructorId,
                         @RequestBody Course c) {
        return service.createCourse(c, instructorId);
    }
}
