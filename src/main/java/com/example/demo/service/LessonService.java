package com.example.demo.service;

import com.example.demo.entity.MicroLesson;

import java.util.List;

public interface LessonService {

    MicroLesson createLesson(MicroLesson lesson);

    MicroLesson getLessonById(Long lessonId);

    List<MicroLesson> searchLessons(String tag, String difficulty, String contentType);
}
