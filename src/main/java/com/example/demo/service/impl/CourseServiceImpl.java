package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    
    @Override
    public Course createCourse(Course course, Long instructorId) {
        User instructor = userRepository.findById(instructorId)
            .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        
        if (!instructor.getRole().equals("INSTRUCTOR") && !instructor.getRole().equals("ADMIN")) {
            throw new RuntimeException("User must be an instructor or admin to create courses");
        }
        
        if (courseRepository.existsByTitleAndInstructorId(course.getTitle(), instructorId)) {
            throw new RuntimeException("Course with this title already exists for this instructor");
        }
        
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }
    
    @Override
    public Course updateCourse(Long courseId, Course courseUpdates) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        
        if (courseUpdates.getTitle() != null) {
            course.setTitle(courseUpdates.getTitle());
        }
        if (courseUpdates.getDescription() != null) {
            course.setDescription(courseUpdates.getDescription());
        }
        if (courseUpdates.getCategory() != null) {
            course.setCategory(courseUpdates.getCategory());
        }
        
        return courseRepository.save(course);
    }
    
    @Override
    public List<Course> listCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
    
    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }
}