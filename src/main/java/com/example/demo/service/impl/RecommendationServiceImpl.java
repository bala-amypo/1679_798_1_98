package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Override
    public List<Course> getRecommendedCourses(Long userId) {
        // Dummy implementation, return empty list
        return new ArrayList<>();
    }
}
