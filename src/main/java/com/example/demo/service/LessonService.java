package com.example.demo.service;

import com.example.demo.model.MicroLesson;
import java.util.List;

public interface LessonService {
    MicroLesson addLesson(Long courseId, MicroLesson lesson);
    MicroLesson updateLesson(Long lessonId, MicroLesson update);
    MicroLesson getLesson(Long lessonId);
    List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType);
}
