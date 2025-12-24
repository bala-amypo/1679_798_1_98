package com.example.demo.service;

import com.example.demo.model.MicroLesson;
import java.util.List;

public interface LessonService {
    List<MicroLesson> getAllLessons();
    MicroLesson getLessonById(Long id);
    MicroLesson saveLesson(MicroLesson lesson);
    MicroLesson updateLesson(Long id, MicroLesson lesson);
    void deleteLesson(Long id);
}
