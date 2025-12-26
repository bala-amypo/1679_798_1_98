@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        return service.createCourse(course);
    }

    @PutMapping("/{courseId}")
    public Course update(@PathVariable Long courseId,
                         @RequestBody Course course) {
        return service.updateCourse(courseId, course);
    }

    @GetMapping("/{courseId}")
    public Course get(@PathVariable Long courseId) {
        return service.getCourse(courseId);
    }

    @GetMapping("/instructor/{instructorId}")
    public List<Course> byInstructor(@PathVariable Long instructorId) {
        return service.getInstructorCourses(instructorId);
    }
}
