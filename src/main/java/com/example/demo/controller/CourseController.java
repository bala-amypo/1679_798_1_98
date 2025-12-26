package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return service.getCourseById(id);
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return service.getAllCourses();
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course c) {
        return service.updateCourse(id, c);
    }
}
