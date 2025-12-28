package com.example.demo.controller;
import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/lessons")
@SecurityRequirement(name = "bearerAuth")   
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/{lessonId}")
    public MicroLesson addLesson(@PathVariable Long lessonId,
                                 @RequestBody MicroLesson lesson) {
        return lessonService.addLesson(lessonId, lesson);
    }

    @GetMapping("/search")
    public List<MicroLesson> searchLessons(@RequestParam(required = false) String tags,
                                           @RequestParam(required = false) String difficulty,
                                           @RequestParam(required = false) String contentType) {
        return lessonService.findLessonsByFilters(tags, difficulty, contentType);
    }

    @PutMapping("/{lessonId}")
    public MicroLesson updateLesson(@PathVariable Long lessonId,
                                    @RequestBody MicroLesson lesson) {
        return lessonService.updateLesson(lessonId, lesson);
    }

    @GetMapping("/{lessonId}")
    public MicroLesson getLesson(@PathVariable Long lessonId) {
        return lessonService.getLesson(lessonId);
    }
}
