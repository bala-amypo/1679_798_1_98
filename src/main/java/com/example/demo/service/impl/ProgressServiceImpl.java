package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;

import java.util.List;

public class ProgressServiceImpl {

    private final ProgressRepository repo;
    private final UserRepository userRepo;
    private final MicroLessonRepository lessonRepo;

    public ProgressServiceImpl(ProgressRepository repo, UserRepository userRepo, MicroLessonRepository lessonRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.lessonRepo = lessonRepo;
    }

    public Progress recordProgress(Long userId, Long lessonId, Progress incoming) {
        User u = userRepo.findById(userId).orElseThrow();
        MicroLesson ml = lessonRepo.findById(lessonId).orElseThrow();

        Progress p = repo.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElse(Progress.builder().user(u).microLesson(ml).build());

        p.setProgressPercent(incoming.getProgressPercent());
        p.setStatus(incoming.getStatus());
        p.setScore(incoming.getScore());

        return repo.save(p);
    }

    public List<Progress> getUserProgress(Long userId) {
        return repo.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}
