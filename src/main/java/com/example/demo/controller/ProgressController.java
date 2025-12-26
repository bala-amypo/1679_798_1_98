@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService service;

    public ProgressController(ProgressService service) {
        this.service = service;
    }

    @PostMapping("/{lessonId}")
    public Progress save(@PathVariable Long lessonId,
                         @RequestBody Progress progress) {
        return service.saveProgress(lessonId, progress);
    }

    @GetMapping("/lesson/{lessonId}")
    public Progress getLessonProgress(@PathVariable Long lessonId) {
        return service.getLessonProgress(lessonId);
    }

    @GetMapping("/user/{userId}")
    public List<Progress> getUserProgress(@PathVariable Long userId) {
        return service.getUserProgress(userId);
    }
}
