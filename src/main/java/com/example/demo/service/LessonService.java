package com.example.demo.service;

import com.example.demo.model.MicroLesson;
import java.util.List;

public interface LessonService {
    List<MicroLesson> getAllLessons();
    MicroLesson getLesson(Long id);
    int getTotalDuration();

    // Add these methods
    MicroLesson addLesson(Long courseId, MicroLesson lesson);
    List<MicroLesson> findLessonsByFilters(String tag, String difficulty, String contentType);
}
