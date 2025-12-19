package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Required exact signature for test cases
    boolean existsByTitleAndInstructorId(String title, Long instructorId);

    // Optional helper method
    List<Course> findByInstructorId(Long instructorId);
}
