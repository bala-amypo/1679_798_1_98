package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Check if a course exists for a given title and instructor
    boolean existsByTitleAndInstructorId(String title, Long instructorId);

    // Find all courses for a given instructor
    List<Course> findByInstructorId(Long instructorId);

    // Optional helper method: find all course IDs
    default List<Long> findAllCourseIds() {
        return findAll().stream()
                        .map(Course::getId)
                        .toList();
    }
}
