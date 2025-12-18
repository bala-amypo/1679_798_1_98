package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Custom method required for test cases
    boolean existsByTitleAndInstructorId(String title, Long instructorId);

    // Optional: for listing courses by instructor (could also use findAll() + filter)
    List<Course> findByInstructorId(Long instructorId);
}
