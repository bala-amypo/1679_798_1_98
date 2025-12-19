package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository lessonRepository;

    public ProgressServiceImpl(ProgressRepository progressRepository,
                               UserRepository userRepository,
                               MicroLessonRepository lessonRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
    }

    // ---------------- RECORD/UPDATE PROGRESS ----------------
    @Override
    public Progress recordProgress(Long userId, Long lessonId, Progress progress) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MicroLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        Progress existing = progressRepository
                .findByUserIdAndMicroLessonId(userId, lessonId)
                .orElse(null);

        if (progress.getProgressPercent() < 0 || progress.getProgressPercent() > 100) {
            throw new IllegalArgumentException("Progress percent must be 0-100");
        }

        if ("COMPLETED".equalsIgnoreCase(progress.getStatus())
                && progress.getProgressPercent() != 100) {
            throw new IllegalArgumentException("Completed status must have 100% progress");
        }

        if (existing == null) {
            existing = Progress.builder()
                    .user(user)
                    .microLesson(lesson)
                    .build();
        }

        existing.setStatus(progress.getStatus());
        existing.setProgressPercent(progress.getProgressPercent());
        existing.setScore(progress.getScore());
        existing.setLastAccessedAt(LocalDateTime.now());

        if ("COMPLETED".equalsIgnoreCase(progress.getStatus())) {
            existing.setCompletedAt(LocalDateTime.now());
        }

        return progressRepository.save(existing);
    }

    // ---------------- GET PROGRESS FOR LESSON ----------------
    @Override
    public Progress getProgress(Long userId, Long lessonId) {
        return progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));
    }

    // ---------------- GET ALL PROGRESS FOR USER ----------------
    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}
