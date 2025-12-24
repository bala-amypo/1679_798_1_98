package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LessonServiceImpl {
    private final MicroLessonRepository lessonRepo;
    private final CourseRepository courseRepo;

    public LessonServiceImpl(MicroLessonRepository lessonRepo, CourseRepository courseRepo) {
        this.lessonRepo = lessonRepo;
        this.courseRepo = courseRepo;
    }

    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course c = courseRepo.findById(courseId).orElseThrow();
        lesson.setCourse(c);
        return lessonRepo.save(lesson);
    }

    public MicroLesson updateLesson(Long id, MicroLesson update) {
        MicroLesson ml = lessonRepo.findById(id).orElseThrow();
        if (update.getTitle() != null) ml.setTitle(update.getTitle());
        if (update.getContentType() != null) ml.setContentType(update.getContentType());
        if (update.getDifficulty() != null) ml.setDifficulty(update.getDifficulty());
        return lessonRepo.save(ml);
    }

    public MicroLesson getLesson(Long id) {
        return lessonRepo.findById(id).orElseThrow();
    }

    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String type) {
        return lessonRepo.findByFilters(tags, difficulty, type);
    }
}
