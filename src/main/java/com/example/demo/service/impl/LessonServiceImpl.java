package com.example.demo.service.impl;

import com.example.demo.entity.MicroLesson;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        return lesson; // dummy
    }

    public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
        return lesson;
    }

    public List<MicroLesson> findLessonsByFilters(String t, String d, String c) {
        return Collections.emptyList();
    }

    public MicroLesson getLesson(Long lessonId) {
        return new MicroLesson();
    }
}
