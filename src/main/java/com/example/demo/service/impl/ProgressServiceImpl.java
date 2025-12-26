package com.example.demo.service.impl;

import com.example.demo.model.Progress;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Override
    public Progress getProgressByMicroLessonId(Long microLessonId) {
        return progressRepository.findByMicroLesson_Id(microLessonId);
    }

    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUser_Id(userId);
    }

    @Override
    public Progress saveProgress(Progress progress) {
        return progressRepository.save(progress);
    }
}
