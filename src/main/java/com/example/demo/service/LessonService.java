package com.example.demo.service;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.MicroLesson;

import java.util.List;

public interface LessonService {

    MicroLesson addLesson(Long courseId, RecommendationRequest request);

    MicroLesson updateLesson(Long lessonId, RecommendationRequest request);

    List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType);

    MicroLesson getLesson(Long lessonId);
}
