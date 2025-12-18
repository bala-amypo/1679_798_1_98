package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    // ✅ Constructor Injection (MANDATORY)
    public LessonServiceImpl(
            MicroLessonRepository lessonRepository,
            CourseRepository courseRepository
    ) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
        MicroLesson existing = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        existing.setTitle(lesson.getTitle());
        existing.setTags(lesson.getTags());
        existing.setDifficulty(lesson.getDifficulty());
        existing.setContentType(lesson.getContentType());
        existing.setDurationMinutes(lesson.getDurationMinutes());

        return lessonRepository.save(existing);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        // ✅ MUST use repository method with exact name
        return lessonRepository.findByFilters(tags, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }
}
