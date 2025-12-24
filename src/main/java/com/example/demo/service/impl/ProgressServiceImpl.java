package com.example.demo.service.impl;

import com.example.demo.entity.Progress;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressServiceImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    public Progress markProgress(Long userId, Long lessonId, boolean completed) {
        Progress progress = progressRepository
                .findByUserIdAndMicroLessonId(userId, lessonId)
                .orElse(new Progress());

        progress.setUserId(userId);
        progress.setMicroLessonId(lessonId);
        progress.setCompleted(completed);
        progress.setLastAccessedAt(LocalDateTime.now());

        return progressRepository.save(progress);
    }

    @Override
    public Progress getProgress(Long userId, Long lessonId) {
        return progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));
    }

    @Override
    public List<Progress> getRecentProgress(Long userId) {
        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}
