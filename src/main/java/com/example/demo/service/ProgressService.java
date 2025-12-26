// package com.example.demo.service;

// import com.example.demo.model.Progress;

// import java.util.List;

// public interface ProgressService {

//     Progress getProgressByMicroLessonId(Long microLessonId);

//     List<Progress> getUserProgress(Long userId);

//     Progress saveProgress(Progress progress);
// }


package com.example.demo.service;

import com.example.demo.entity.Progress;
import java.util.List;

public interface ProgressService {
    Progress recordProgress(Long userId, Long lessonId, Progress progress);
    Progress getProgress(Long userId, Long lessonId);
    List<Progress> getUserProgress(Long userId);
}