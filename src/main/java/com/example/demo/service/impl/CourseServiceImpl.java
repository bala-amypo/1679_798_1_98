package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private List<Course> courses = new ArrayList<>();

    @Override
    public List<Course> getAllCourses() {
        return courses;
    }

    @Override
    public List<Course> listCoursesByInstructor(Long instructorId) {
        List<Course> result = new ArrayList<>();
        for (Course c : courses) {
            if (c.getInstructor() != null && c.getInstructor().getId().equals(instructorId)) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public Course saveCourse(Course course) {
        courses.add(course);
        return course;
    }

    @Override
    public Course updateCourse(Long id, Course update) {
        for (Course c : courses) {
            if (c.getId().equals(id)) {
                c.setTitle(update.getTitle());
                c.setDescription(update.getDescription());
                c.setInstructor(update.getInstructor());
                return c;
            }
        }
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        courses.removeIf(c -> c.getId().equals(id));
    }
}
