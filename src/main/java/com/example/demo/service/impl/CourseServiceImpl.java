package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository,
                             UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // ---------------- CREATE COURSE ----------------
    @Override
    public Course createCourse(Course course, Long instructorId) {

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        if (!instructor.getRole().equalsIgnoreCase("INSTRUCTOR") &&
            !instructor.getRole().equalsIgnoreCase("ADMIN")) {
            throw new IllegalArgumentException("User is not allowed to create courses");
        }

        if (course.getTitle() == null || course.getTitle().isBlank()) {
            throw new IllegalArgumentException("Course title is required");
        }

        if (courseRepository.existsByTitleAndInstructorId(course.getTitle(), instructorId)) {
            throw new IllegalArgumentException("Course title already exists for this instructor");
        }

        course.setInstructor(instructor);

        return courseRepository.save(course);
    }

    // ---------------- UPDATE COURSE ----------------
    @Override
    public Course updateCourse(Long courseId, Course updatedCourse) {

        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (updatedCourse.getTitle() != null && !updatedCourse.getTitle().isBlank()) {
            // Check uniqueness if title changed
            if (!existingCourse.getTitle().equals(updatedCourse.getTitle()) &&
                courseRepository.existsByTitleAndInstructorId(updatedCourse.getTitle(),
                                                             existingCourse.getInstructor().getId())) {
                throw new IllegalArgumentException("Course title already exists for this instructor");
            }
            existingCourse.setTitle(updatedCourse.getTitle());
        }

        if (updatedCourse.getDescription() != null) {
            existingCourse.setDescription(updatedCourse.getDescription());
        }

        if (updatedCourse.getCategory() != null) {
            existingCourse.setCategory(updatedCourse.getCategory());
        }

        return courseRepository.save(existingCourse);
    }

    // ---------------- LIST COURSES BY INSTRUCTOR ----------------
    @Override
    public List<Course> listCoursesByInstructor(Long instructorId) {

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        return courseRepository.findByInstructorId(instructorId);
    }

    // ---------------- GET COURSE ----------------
    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }
}
