package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Courses", description = "Course management APIs")
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Create a new course")
    @PostMapping
    public Course createCourse(@RequestParam Long instructorId, @RequestBody Course course) {
        return courseService.createCourse(course, instructorId);
    }

    @Operation(summary = "Update a course")
    @PutMapping("/{courseId}")
    public Course updateCourse(@PathVariable Long courseId, @RequestBody Course course) {
        return courseService.updateCourse(courseId, course);
    }

    @Operation(summary = "Get courses by instructor")
    @GetMapping("/instructor/{instructorId}")
    public List<Course> getByInstructor(@PathVariable Long instructorId) {
        return courseService.listCoursesByInstructor(instructorId);
    }

    @Operation(summary = "Get course details")
    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable Long courseId) {
        return courseService.getCourse(courseId);
    }
}
