package com.example.demo.service;

import com.example.demo.entity.Course;

public interface CourseService {
    Course createCourse(Course course, Long instructorId);
}
