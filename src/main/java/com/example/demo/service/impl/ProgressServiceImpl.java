package com.example.demo.service.impl;

import com.example.demo.entity.MicroLesson;
import com.example.demo.entity.Progress;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    @Override
    public Progress recordProgress(Long userId, Long lessonId, Progress progress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MicroLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        if (progress.getProgressPercent() < 0 || progress.getProgressPercent() > 100) {
            throw new IllegalArgumentException("Invalid progress percent");
        }

        if ("COMPLETED".equals(progress.getStatus()) && progress.getProgressPercent() != 100) {
            throw new IllegalArgumentException("Completed progress must be 100%");
        }

        Progress existing = progressRepository
                .findByUserIdAndMicroLessonId(userId, lessonId)
                .orElse(null);

        if (existing == null) {
            progress.setUser(user);
            progress.setMicroLesson(lesson);
            return progressRepository.save(progress);
        }

        existing.setStatus(progress.getStatus());
        existing.setProgressPercent(progress.getProgressPercent());
        existing.setScore(progress.getScore());

        return progressRepository.save(existing);
    }

    @Override
    public Progress getProgress(Long userId, Long lessonId) {
        return progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));
    }

    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}
