
package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping
    @Operation(summary = "Create a new course (INSTRUCTOR/ADMIN)")
    public ResponseEntity<Course> createCourse(@RequestParam Long instructorId,
                                               @RequestBody Course course) {
        Course created = courseService.createCourse(course, instructorId);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Update an existing course")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId,
                                               @RequestBody Course course) {
        Course updated = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "List all courses of an instructor")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(courseService.listCoursesByInstructor(instructorId));
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Get course details by ID")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }
}
