@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public Recommendation generate(@RequestBody Recommendation r) {
        return service.generate(r);
    }

    @GetMapping("/latest/{userId}")
    public Recommendation latest(@PathVariable Long userId) {
        return service.getLatestRecommendation(userId);
    }

    @GetMapping("/user/{userId}")
    public List<Recommendation> all(@PathVariable Long userId) {
        return service.getUserRecommendations(userId);
    }
}
