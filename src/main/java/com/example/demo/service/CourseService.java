package com.example.demo.service;

import com.example.demo.model.Course;
import java.util.List;

public interface CourseService {
    Course createCourse(Course course, Long instructorId);
    Course getCourse(Long courseId);
    List<Course> getAllCourses();
    List<Course> listCoursesByInstructor(Long instructorId);
}
