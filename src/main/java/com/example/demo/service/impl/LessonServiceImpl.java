package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Course;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository microLessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(MicroLessonRepository microLessonRepository,
                             CourseRepository courseRepository) {
        this.microLessonRepository = microLessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id: " + courseId));

        if (lesson.getDurationMinutes() == null ||
                lesson.getDurationMinutes() <= 0 ||
                lesson.getDurationMinutes() > 15) {
            throw new IllegalArgumentException("Lesson duration must be between 1 and 15 minutes");
        }

        // ✅ FIX: relationship is object-based, not ID-based
        lesson.setCourse(course);

        return microLessonRepository.save(lesson);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return microLessonRepository.findByFilters(tags, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return microLessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lesson not found with id: " + lessonId));
    }

    @Override
    public List<MicroLesson> getAllLessons() {
        return microLessonRepository.findAll();
    }

    @Override
    public int getTotalDuration() {
        return microLessonRepository.findAll()
                .stream()
                .mapToInt(MicroLesson::getDurationMinutes)
                .sum();
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
        MicroLesson existing = microLessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lesson not found with id: " + lessonId));

        if (lesson.getTitle() != null) {
            existing.setTitle(lesson.getTitle());
        }

        if (lesson.getContentType() != null) {
            existing.setContentType(lesson.getContentType());
        }

        if (lesson.getDifficulty() != null) {
            existing.setDifficulty(lesson.getDifficulty());
        }

        // ✅ FIX: field name is "tags", not "tag"
        if (lesson.getTags() != null) {
            existing.setTags(lesson.getTags());
        }

        if (lesson.getDurationMinutes() != null && lesson.getDurationMinutes() > 0) {
            existing.setDurationMinutes(lesson.getDurationMinutes());
        }

        return microLessonRepository.save(existing);
    }
}
