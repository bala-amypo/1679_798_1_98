package com.example.demo.service;

import com.example.demo.model.Course;
import java.util.List;

public interface RecommendationService {
    List<Course> getRecommendedCourses(Long userId);
}
