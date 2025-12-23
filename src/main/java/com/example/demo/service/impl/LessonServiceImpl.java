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
    public List<MicroLesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public MicroLesson getLesson(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public int getTotalDuration() {
        int total = 0;
        for (MicroLesson lesson : lessonRepository.findAll()) {
            total += lesson.getDurationMinutes();
        }
        return total;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        lesson.setCourseId(courseId); // assuming MicroLesson has setCourseId
        return lessonRepository.save(lesson);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tag, String difficulty, String contentType) {
        // You need a repository method for filtering, or simple placeholder
        return lessonRepository.findAll(); // placeholder, implement filtering in repo if needed
    }
}
