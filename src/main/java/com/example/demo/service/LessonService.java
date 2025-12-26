package com.example.demo.service;

import com.example.demo.model.MicroLesson;
import java.util.List;

public interface LessonService {

    MicroLesson createLesson(MicroLesson lesson);

    MicroLesson updateLesson(Long lessonId, MicroLesson lesson);

    List<MicroLesson> getLessonsByCourse(Long courseId);

    MicroLesson getLessonById(Long lessonId);
}
