// package com.example.demo.controller;

// import org.springframework.web.bind.annotation.*;
// import org.springframework.http.ResponseEntity;

// import java.util.List;

// import com.example.demo.model.Course;
// import com.example.demo.service.CourseService;

// @RestController
// @RequestMapping("/courses")
// public class CourseController {

//     private final CourseService courseService;

//     public CourseController(CourseService courseService) {
//         this.courseService = courseService;
//     }

//     // POST /courses
//     @PostMapping
//     public ResponseEntity<Course> createCourse(@RequestBody Course course) {
//         return ResponseEntity.ok(courseService.createCourse(course));
//     }

//     // PUT /courses/{courseId}
//     @PutMapping("/{courseId}")
//     public ResponseEntity<Course> updateCourse(
//             @PathVariable Long courseId,
//             @RequestBody Course course) {
//         return ResponseEntity.ok(courseService.updateCourse(courseId, course));
//     }

//     // GET /courses/instructor/{instructorId}
//     @GetMapping("/instructor/{instructorId}")
//     public ResponseEntity<List<Course>> getInstructorCourses(
//             @PathVariable Long instructorId) {
//         return ResponseEntity.ok(courseService.getCoursesByInstructor(instructorId));
//     }

//     // GET /courses/{courseId}
//     @GetMapping("/{courseId}")
//     public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
//         return ResponseEntity.ok(courseService.getCourseById(courseId));
//     }
// }


package com.example.demo.controller;

import com.example.demo.entity.Course;
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
    
    @PostMapping
    public ResponseEntity<Course> createCourse(
            @RequestBody Course course,
            @RequestParam Long instructorId) {
        Course created = courseService.createCourse(course, instructorId);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course) {
        Course updated = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Course>> getInstructorCourses(
            @PathVariable Long instructorId) {
        List<Course> courses = courseService.listCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }
}