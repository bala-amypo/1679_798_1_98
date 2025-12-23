package com.example.demo.service;

import com.example.demo.model.MicroLesson;
import java.util.List;

public interface LessonService {

    List<MicroLesson> getAllLessons();

    MicroLesson getLesson(Long id);

    MicroLesson addLesson(Long courseId, MicroLesson lesson);

    MicroLesson updateLesson(Long lessonId, MicroLesson lesson);

    List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType);

    int getTotalDuration(); // Keep this if you plan to calculate total duration of all lessons
}
