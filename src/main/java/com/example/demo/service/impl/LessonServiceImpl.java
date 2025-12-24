package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private List<MicroLesson> lessons = new ArrayList<>();

    @Override
    public List<MicroLesson> getAllLessons() {
        return lessons;
    }

    @Override
    public MicroLesson getLessonById(Long id) {
        return lessons.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public MicroLesson saveLesson(MicroLesson lesson) {
        lessons.add(lesson);
        return lesson;
    }

    @Override
    public MicroLesson updateLesson(Long id, MicroLesson update) {
        for (MicroLesson lesson : lessons) {
            if (lesson.getId().equals(id)) {
                lesson.setTitle(update.getTitle());
                lesson.setContentType(update.getContentType());
                lesson.setDifficulty(update.getDifficulty());
                return lesson;
            }
        }
        return null;
    }

    @Override
    public void deleteLesson(Long id) {
        lessons.removeIf(l -> l.getId().equals(id));
    }
}
