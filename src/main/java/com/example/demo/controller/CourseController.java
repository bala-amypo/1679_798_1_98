package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course Management")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course,
                               @RequestParam Long instructorId) {
        return courseService.createCourse(course, instructorId);
    }

    @PutMapping("/{courseId}")
    public Course updateCourse(@PathVariable Long courseId,
                               @RequestBody Course course) {
        return courseService.updateCourse(courseId, course);
    }

    @GetMapping("/instructor/{instructorId}")
    public List<Course> getCoursesByInstructor(@PathVariable Long instructorId) {
        return courseService.listCoursesByInstructor(instructorId);
    }

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable Long courseId) {
        return courseService.getCourse(courseId);
    }
}
