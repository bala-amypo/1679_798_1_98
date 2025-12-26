package com.example.demo.service;

import com.example.demo.entity.MicroLesson;

public interface LessonService {
    MicroLesson addLesson(Long courseId, MicroLesson lesson);
}
