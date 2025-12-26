package com.example.demo.service;

import com.example.demo.entity.MicroLesson;
import com.example.demo.entity.Course;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    // POST
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    // GET
    public MicroLesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public List<MicroLesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    // PUT
    public MicroLesson updateLesson(Long id, MicroLesson lesson) {
        MicroLesson existing = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Lesson not found"));
        existing.setTitle(lesson.getTitle());
        existing.setContent(lesson.getContent());
        return lessonRepository.save(existing);
    }
}
