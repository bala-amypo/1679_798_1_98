package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(MicroLessonRepository lessonRepository,
                             CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    // ---------------- ADD LESSON ----------------
    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (lesson.getDurationMinutes() == null || lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
            throw new IllegalArgumentException("Duration must be between 1 and 15 minutes");
        }

        if (!isValidContentType(lesson.getContentType())) {
            throw new IllegalArgumentException("Invalid content type");
        }

        if (!isValidDifficulty(lesson.getDifficulty())) {
            throw new IllegalArgumentException("Invalid difficulty");
        }

        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    // ---------------- UPDATE LESSON ----------------
    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson updatedLesson) {

        MicroLesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        if (updatedLesson.getTitle() != null && !updatedLesson.getTitle().isBlank()) {
            existingLesson.setTitle(updatedLesson.getTitle());
        }

        if (updatedLesson.getDurationMinutes() != null) {
            if (updatedLesson.getDurationMinutes() <= 0 || updatedLesson.getDurationMinutes() > 15) {
                throw new IllegalArgumentException("Duration must be between 1 and 15 minutes");
            }
            existingLesson.setDurationMinutes(updatedLesson.getDurationMinutes());
        }

        if (updatedLesson.getContentType() != null && isValidContentType(updatedLesson.getContentType())) {
            existingLesson.setContentType(updatedLesson.getContentType());
        }

        if (updatedLesson.getDifficulty() != null && isValidDifficulty(updatedLesson.getDifficulty())) {
            existingLesson.setDifficulty(updatedLesson.getDifficulty());
        }

        if (updatedLesson.getTags() != null) {
            existingLesson.setTags(updatedLesson.getTags());
        }

        if (updatedLesson.getPublishDate() != null) {
            existingLesson.setPublishDate(updatedLesson.getPublishDate());
        }

        return lessonRepository.save(existingLesson);
    }

    // ---------------- GET LESSON ----------------
    @Override
    public MicroLesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }

    // ---------------- SEARCH LESSONS ----------------
    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return lessonRepository.findByFilters(tags, difficulty, contentType);
    }

    // ---------------- VALIDATORS ----------------
    private boolean isValidContentType(String type) {
        return type != null && (type.equalsIgnoreCase("VIDEO")
                || type.equalsIgnoreCase("ARTICLE")
                || type.equalsIgnoreCase("QUIZ")
                || type.equalsIgnoreCase("INTERACTIVE"));
    }

    private boolean isValidDifficulty(String difficulty) {
        return difficulty != null && (difficulty.equalsIgnoreCase("BEGINNER")
                || difficulty.equalsIgnoreCase("INTERMEDIATE")
                || difficulty.equalsIgnoreCase("ADVANCED"));
    }
}
