package com.example.demo.service;

import com.example.demo.model.MicroLesson;

public interface LessonService {
    MicroLesson addLesson(Long courseId, MicroLesson lesson);
}
