package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository lessonRepository;

    public LessonServiceImpl(MicroLessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public MicroLesson createLesson(MicroLesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
        MicroLesson existing = getLessonById(lessonId);
        existing.setTitle(lesson.getTitle());
        existing.setContent(lesson.getContent());
        return lessonRepository.save(existing);
    }

    @Override
    public List<MicroLesson> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    @Override
    public MicroLesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }
}
