package com.example.demo.service.impl;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private List<Progress> progresses = new ArrayList<>();

    @Override
    public Progress getProgress(Long userId, Long lessonId) {
        return progresses.stream()
                .filter(p -> p.getUser().getId().equals(userId) && p.getMicroLesson().getId().equals(lessonId))
                .findFirst().orElse(null);
    }

    @Override
    public Progress saveProgress(Progress progress) {
        progresses.add(progress);
        return progress;
    }

    @Override
    public Progress updateProgress(Long id, Progress incoming) {
        for (Progress p : progresses) {
            if (p.getId().equals(id)) {
                p.setScore(incoming.getScore());
                p.setStatus(incoming.getStatus());
                p.setProgressPercent(incoming.getProgressPercent());
                return p;
            }
        }
        return null;
    }
}
