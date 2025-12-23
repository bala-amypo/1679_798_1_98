package com.example.demo.service;

import com.example.demo.model.MicroLesson;
import java.util.List;

public interface LessonService {
    List<MicroLesson> getAllLessons();
    MicroLesson getLesson(Long id);  // required abstract method
    int getTotalDuration();
}
