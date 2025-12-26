package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository microLessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public MicroLesson addLesson(Long courseId, RecommendationRequest request) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (request.getDurationMinutes() == null || request.getDurationMinutes() <= 0) {
            throw new RuntimeException("Duration must be positive");
        }

        if (request.getDurationMinutes() > 15) {
            throw new RuntimeException("Duration cannot exceed 15 minutes for micro-learning");
        }

        MicroLesson lesson = new MicroLesson();
        lesson.setTitle(request.getTitle());
        lesson.setDurationMinutes(request.getDurationMinutes());
        lesson.setContentType(request.getContentType());
        lesson.setDifficulty(request.getDifficulty());
        lesson.setTags(request.getTags());
        lesson.setPublishDate(request.getPublishDate());
        lesson.setCourse(course);

        return microLessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, RecommendationRequest request) {

        MicroLesson lesson = microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        if (request.getTitle() != null) {
            lesson.setTitle(request.getTitle());
        }

        if (request.getDurationMinutes() != null) {
            if (request.getDurationMinutes() <= 0) {
                throw new RuntimeException("Duration must be positive");
            }
            if (request.getDurationMinutes() > 15) {
                throw new RuntimeException("Duration cannot exceed 15 minutes for micro-learning");
            }
            lesson.setDurationMinutes(request.getDurationMinutes());
        }

        if (request.getContentType() != null) {
            lesson.setContentType(request.getContentType());
        }

        if (request.getDifficulty() != null) {
            lesson.setDifficulty(request.getDifficulty());
        }

        if (request.getTags() != null) {
            lesson.setTags(request.getTags());
        }

        if (request.getPublishDate() != null) {
            lesson.setPublishDate(request.getPublishDate());
        }

        return microLessonRepository.save(lesson);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return microLessonRepository.findByFilters(tags, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }
}
