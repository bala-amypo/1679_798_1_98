package com.example.demo.service.impl;

import com.example.demo.entity.MicroLesson;
import com.example.demo.exception.ResourceNotFoundException;
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
    public MicroLesson createLesson(MicroLesson lesson) {
        return microLessonRepository.save(lesson);
    }

    @Override
    public MicroLesson getLessonById(Long lessonId) {
        return microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }

    @Override
    public List<MicroLesson> searchLessons(String tag, String difficulty, String contentType) {
        return microLessonRepository.findByFilters(tag, difficulty, contentType);
    }
}
