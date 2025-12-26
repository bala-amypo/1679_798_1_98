package com.example.demo.service;

import com.example.demo.model.Progress;

public interface ProgressService {
    Progress recordProgress(Long userId, Long lessonId, Progress progress);
}
