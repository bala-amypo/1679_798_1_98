package com.example.demo.service.impl;

import com.example.demo.entity.Progress;
import com.example.demo.entity.MicroLesson;
import com.example.demo.entity.User;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository lessonRepository;

    // ✅ Constructor Injection (MANDATORY)
    public ProgressServiceImpl(
            ProgressRepository progressRepository,
            UserRepository userRepository,
            MicroLessonRepository lessonRepository
    ) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Progress recordProgress(Long userId, Long lessonId, Progress progress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MicroLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        progress.setUser(user);
        progress.setMicroLesson(lesson);

        return progressRepository.save(progress);
    }

    @Override
    public Progress getProgress(Long userId, Long lessonId) {

        return progressRepository.findByUserIdAndMicroLessonId(userId, lessonId);
    }

    @Override
    public List<Progress> getUserProgress(Long userId) {

        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}
