// package com.example.demo.service.impl;

// import com.example.demo.model.Progress;
// import com.example.demo.repository.ProgressRepository;
// import com.example.demo.service.ProgressService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class ProgressServiceImpl implements ProgressService {

//     @Autowired
//     private ProgressRepository progressRepository;

//     @Override
//     public Progress getProgressByMicroLessonId(Long microLessonId) {
//         return progressRepository.findByMicroLesson_Id(microLessonId);
//     }

//     @Override
//     public List<Progress> getUserProgress(Long userId) {
//         return progressRepository.findByUser_Id(userId);
//     }

//     @Override
//     public Progress saveProgress(Progress progress) {
//         return progressRepository.save(progress);
//     }
// }


package com.example.demo.service.impl;

import com.example.demo.entity.MicroLesson;
import com.example.demo.entity.Progress;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {
    
    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    
    @Override
    public Progress recordProgress(Long userId, Long lessonId, Progress progressUpdate) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        MicroLesson microLesson = microLessonRepository.findById(lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        
        if (progressUpdate.getProgressPercent() == null || 
            progressUpdate.getProgressPercent() < 0 || 
            progressUpdate.getProgressPercent() > 100) {
            throw new RuntimeException("Progress percent must be between 0 and 100");
        }
        
        if ("COMPLETED".equals(progressUpdate.getStatus()) && 
            progressUpdate.getProgressPercent() != 100) {
            throw new RuntimeException("Progress must be 100% when status is COMPLETED");
        }
        
        Optional<Progress> existingProgress = progressRepository
            .findByUserIdAndMicroLessonId(userId, lessonId);
        
        Progress progress;
        if (existingProgress.isPresent()) {
            progress = existingProgress.get();
            if (progressUpdate.getStatus() != null) {
                progress.setStatus(progressUpdate.getStatus());
            }
            if (progressUpdate.getProgressPercent() != null) {
                progress.setProgressPercent(progressUpdate.getProgressPercent());
            }
            if (progressUpdate.getScore() != null) {
                progress.setScore(progressUpdate.getScore());
            }
        } else {
            progress = Progress.builder()
                    .user(user)
                    .microLesson(microLesson)
                    .status(progressUpdate.getStatus())
                    .progressPercent(progressUpdate.getProgressPercent())
                    .score(progressUpdate.getScore())
                    .build();
        }
        
        return progressRepository.save(progress);
    }
    
    @Override
    public Progress getProgress(Long userId, Long lessonId) {
        return progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));
    }
    
    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}