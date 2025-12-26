// package com.example.demo.service.impl;

// import com.example.demo.model.MicroLesson;
// import com.example.demo.repository.MicroLessonRepository;
// import com.example.demo.service.LessonService;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class LessonServiceImpl implements LessonService {

//     private final MicroLessonRepository lessonRepository;

//     public LessonServiceImpl(MicroLessonRepository lessonRepository) {
//         this.lessonRepository = lessonRepository;
//     }

//     @Override
//     public MicroLesson createLesson(MicroLesson lesson) {
//         return lessonRepository.save(lesson);
//     }

//     @Override
//     public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
//         MicroLesson existing = getLessonById(lessonId);
//         existing.setTitle(lesson.getTitle());
//         existing.setContent(lesson.getContent());
//         return lessonRepository.save(existing);
//     }

//     @Override
//     public List<MicroLesson> getLessonsByCourse(Long courseId) {
//         return lessonRepository.findByCourseId(courseId);
//     }

//     @Override
//     public MicroLesson getLessonById(Long lessonId) {
//         return lessonRepository.findById(lessonId)
//                 .orElseThrow(() -> new RuntimeException("Lesson not found"));
//     }
// }


package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.MicroLesson;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    
    private final MicroLessonRepository microLessonRepository;
    private final CourseRepository courseRepository;
    
    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        
        if (lesson.getDurationMinutes() == null || lesson.getDurationMinutes() <= 0) {
            throw new RuntimeException("Duration must be positive");
        }
        
        if (lesson.getDurationMinutes() > 15) {
            throw new RuntimeException("Duration cannot exceed 15 minutes for micro-learning");
        }
        
        lesson.setCourse(course);
        return microLessonRepository.save(lesson);
    }
    
    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson lessonUpdates) {
        MicroLesson lesson = microLessonRepository.findById(lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        
        if (lessonUpdates.getTitle() != null) {
            lesson.setTitle(lessonUpdates.getTitle());
        }
        if (lessonUpdates.getDurationMinutes() != null) {
            if (lessonUpdates.getDurationMinutes() <= 0) {
                throw new RuntimeException("Duration must be positive");
            }
            if (lessonUpdates.getDurationMinutes() > 15) {
                throw new RuntimeException("Duration cannot exceed 15 minutes for micro-learning");
            }
            lesson.setDurationMinutes(lessonUpdates.getDurationMinutes());
        }
        if (lessonUpdates.getContentType() != null) {
            lesson.setContentType(lessonUpdates.getContentType());
        }
        if (lessonUpdates.getDifficulty() != null) {
            lesson.setDifficulty(lessonUpdates.getDifficulty());
        }
        if (lessonUpdates.getTags() != null) {
            lesson.setTags(lessonUpdates.getTags());
        }
        if (lessonUpdates.getPublishDate() != null) {
            lesson.setPublishDate(lessonUpdates.getPublishDate());
        }
        
        return microLessonRepository.save(lesson);
    }
    
    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return microLessonRepository.findByFilters(tags, difficulty, contentType);
    }
    
    @Override
    public MicroLesson getLesson(Long lessonId) {
        return microLessonRepository.findById(lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }
}