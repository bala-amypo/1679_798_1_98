@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @PostMapping("/course/{courseId}")
    public MicroLesson add(@PathVariable Long courseId,
                           @RequestBody MicroLesson lesson) {
        return service.addLesson(courseId, lesson);
    }

    @PutMapping("/{lessonId}")
    public MicroLesson update(@PathVariable Long lessonId,
                              @RequestBody MicroLesson lesson) {
        return service.updateLesson(lessonId, lesson);
    }

    @GetMapping("/{lessonId}")
    public MicroLesson get(@PathVariable Long lessonId) {
        return service.getLesson(lessonId);
    }

    @GetMapping("/search")
    public List<MicroLesson> search(@RequestParam String keyword) {
        return service.searchLessons(keyword);
    }
}
