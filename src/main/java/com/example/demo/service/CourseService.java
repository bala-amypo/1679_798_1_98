package com.example.demo.service;

import com.example.demo.model.Course;
import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();
    List<Course> listCoursesByInstructor(Long instructorId);
    Course saveCourse(Course course);
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);
}
