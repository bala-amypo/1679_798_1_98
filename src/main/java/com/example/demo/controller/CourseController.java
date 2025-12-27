package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course Management")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Create a new course
    // Swagger usage: POST /courses?instructorId=1
    @PostMapping
    public ResponseEntity<Course> createCourse(
            @RequestBody Course course,
            @RequestParam Long instructorId) {
        Course created = courseService.createCourse(course, instructorId);
        return ResponseEntity.ok(created);
    }

    // Update an existing course
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course) {
        Course updated = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updated);
    }

    // Get all courses by instructor
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Course>> getInstructorCourses(
            @PathVariable Long instructorId) {
        List<Course> courses = courseService.listCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }

    // Get a course by ID
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }
}
