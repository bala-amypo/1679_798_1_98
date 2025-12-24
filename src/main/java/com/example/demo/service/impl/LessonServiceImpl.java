package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;

import java.util.List;

public class LessonServiceImpl {

    private final MicroLessonRepository repo;
    private final CourseRepository courseRepo;

    public LessonServiceImpl(MicroLessonRepository repo, CourseRepository courseRepo) {
        this.repo = repo;
        this.courseRepo = courseRepo;
    }

    public MicroLesson addLesson(Long courseId, MicroLesson m) {
        Course c = courseRepo.findById(courseId).orElseThrow();
        m.setCourse(c);
        return repo.save(m);
    }

    public MicroLesson updateLesson(Long id, MicroLesson upd) {
        MicroLesson m = repo.findById(id).orElseThrow();
        m.setTitle(upd.getTitle());
        m.setContentType(upd.getContentType());
        m.setDifficulty(upd.getDifficulty());
        return repo.save(m);
    }

    public MicroLesson getLesson(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public List<MicroLesson> findLessonsByFilters(String t, String d, String c) {
        return repo.findByFilters(t, d, c);
    }
}
