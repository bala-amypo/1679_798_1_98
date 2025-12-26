package com.example.demo.service;

import com.example.demo.entity.Progress;

public interface ProgressService {
    Progress recordProgress(Long userId, Long lessonId, Progress progress);
}
