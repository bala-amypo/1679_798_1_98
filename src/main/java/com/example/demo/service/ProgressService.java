package com.example.demo.service;

import com.example.demo.model.Progress;

public interface ProgressService {
    Progress getProgress(Long userId, Long lessonId);
    Progress saveProgress(Progress progress);
    Progress updateProgress(Long id, Progress progress);
}
