package com.example.demo.service;

import com.example.demo.model.Progress;

import java.util.List;

public interface ProgressService {

    Progress getProgressByMicroLessonId(Long microLessonId);

    List<Progress> getUserProgress(Long userId);

    Progress saveProgress(Progress progress);
}
