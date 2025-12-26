package com.example.demo.service;

import com.example.demo.entity.Progress;
import com.example.demo.entity.User;
import com.example.demo.entity.MicroLesson;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    public ProgressService(ProgressRepository progressRepository, UserRepository userRepository, LessonRepository lessonRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
    }

    // POST
    public Progress recordProgress(Long userId, Long lessonId, Progress progress) {
        User user = userRepository.findById(userId).orElseThrow();
        MicroLesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        progress.setUser(user);
        progress.setLesson(lesson);
        return progressRepository.save(progress);
    }

    // GET
    public Progress getProgress(Long userId, Long lessonId) {
        return progressRepository.findByUserIdAndLessonId(userId, lessonId).orElse(null);
    }

    public List<Progress> getAllProgress(Long userId) {
        return progressRepository.findByUserId(userId);
    }

    // PUT
    public Progress updateProgress(Long userId, Long lessonId, Progress progress) {
        Progress existing = progressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElseThrow(() -> new RuntimeException("Progress not found"));
        existing.setCompleted(progress.isCompleted());
        existing.setScore(progress.getScore());
        return progressRepository.save(existing);
    }
}
