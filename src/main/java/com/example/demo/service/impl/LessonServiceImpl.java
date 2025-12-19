package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.MicroLesson;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(MicroLessonRepository lessonRepository,
                             CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
            throw new IllegalArgumentException("Invalid lesson duration");
        }

        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
        MicroLesson existing = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        existing.setTitle(lesson.getTitle());
        existing.setDurationMinutes(lesson.getDurationMinutes());
        existing.setContentType(lesson.getContentType());
        existing.setDifficulty(lesson.getDifficulty());
        existing.setTags(lesson.getTags());
        existing.setPublishDate(lesson.getPublishDate());

        return lessonRepository.save(existing);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return lessonRepository.findByFilters(tags, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }
}
