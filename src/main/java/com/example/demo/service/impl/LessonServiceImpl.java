package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository microLessonRepository;

    public LessonServiceImpl(MicroLessonRepository microLessonRepository) {
        this.microLessonRepository = microLessonRepository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        lesson.setCourseId(courseId);
        return microLessonRepository.save(lesson);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tag, String difficulty, String contentType) {
        return microLessonRepository.findByTagAndDifficultyAndContentType(tag, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return microLessonRepository.findById(lessonId).orElse(null);
    }
}
