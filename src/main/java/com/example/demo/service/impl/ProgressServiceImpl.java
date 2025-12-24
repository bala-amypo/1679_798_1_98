package com.example.demo.service.impl;

import com.example.demo.model.Progress;
import com.example.demo.model.User;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.MicroLessonRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProgressServiceImpl {
    private final ProgressRepository progressRepo;
    private final UserRepository userRepo;
    private final MicroLessonRepository lessonRepo;

    public ProgressServiceImpl(ProgressRepository progressRepo, UserRepository userRepo, MicroLessonRepository lessonRepo) {
        this.progressRepo = progressRepo;
        this.userRepo = userRepo;
        this.lessonRepo = lessonRepo;
    }

    public Progress recordProgress(Long userId, Long lessonId, Progress incoming) {
        User u = userRepo.findById(userId).orElseThrow();
        MicroLesson ml = lessonRepo.findById(lessonId).orElseThrow();
        Progress p = progressRepo.findByUserIdAndMicroLessonId(userId, lessonId).orElse(incoming);
        p.setUser(u);
        p.setMicroLesson(ml);
        if (incoming.getProgressPercent() != null) p.setProgressPercent(incoming.getProgressPercent());
        if (incoming.getStatus() != null) p.setStatus(incoming.getStatus());
        if (incoming.getScore() != null) p.setScore(incoming.getScore());
        return progressRepo.save(p);
    }

    public List<Progress> getUserProgress(Long userId) {
        return progressRepo.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}
