package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Required by automated test
    boolean existsByTitleAndInstructorId(String title, Long instructorId);
}
