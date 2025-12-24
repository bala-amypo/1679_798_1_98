package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CourseServiceImpl {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Course createCourse(Course c, Long instructorId) {
        User u = userRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("No instructor"));
        if (courseRepository.existsByTitleAndInstructorId(c.getTitle(), instructorId)) throw new RuntimeException("Duplicate");
        c.setInstructor(u);
        return courseRepository.save(c);
    }

    public Course updateCourse(Long id, Course update) {
        Course c = courseRepository.findById(id).orElseThrow();
        if (update.getTitle() != null) c.setTitle(update.getTitle());
        if (update.getDescription() != null) c.setDescription(update.getDescription());
        return courseRepository.save(c);
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }
}
