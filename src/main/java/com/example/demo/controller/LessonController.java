@PostMapping("/course/{courseId}")
public ResponseEntity<MicroLesson> addLesson(
        @PathVariable Long courseId,
        @RequestBody RecommendationRequest request) {

    MicroLesson created = lessonService.addLesson(courseId, request);
    return ResponseEntity.ok(created);
}

@PutMapping("/{lessonId}")
public ResponseEntity<MicroLesson> updateLesson(
        @PathVariable Long lessonId,
        @RequestBody RecommendationRequest request) {

    MicroLesson updated = lessonService.updateLesson(lessonId, request);
    return ResponseEntity.ok(updated);
}
