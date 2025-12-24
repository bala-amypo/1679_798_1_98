package com.example.demo.service.impl;

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

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Course createCourse(Course course, Long instructorId) {
        User instructor = userRepository.findById(instructorId).orElseThrow();
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow();
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> listCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
}
