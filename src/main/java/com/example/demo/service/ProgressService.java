package com.example.demo.service;

import com.example.demo.entity.Progress;

import java.util.List;

public interface ProgressService {

    Progress markProgress(Long userId, Long lessonId, boolean completed);

    Progress getProgress(Long userId, Long lessonId);

    List<Progress> getRecentProgress(Long userId);
}
