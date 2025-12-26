package com.example.demo.service;

import com.example.demo.model.Progress;
import java.util.List;

public interface ProgressService {

    Progress saveOrUpdate(Long userId, Progress progress);

    Progress getProgressForLesson(Long lessonId);

    List<Progress> getUserProgress(Long userId);
}
