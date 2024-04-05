package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.Course;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.exceptionHandler.*;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course) {
        if (course.getCourseName() == null || course.getCourseName().isEmpty() ||
                course.getLevel() == null || course.getLevel().isEmpty()) {
            throw new InvalidCourseDataException("Course name, description, and level cannot be null or empty.");
        }
        Optional<Course> c = courseRepository.findByCourseName(course.getCourseName());
        if (c != null) {
            throw new DuplicateCourseException("Course with name '" + course.getCourseName() + "' already exists.");
        }

        try {
            return courseRepository.save(course);
        } catch (Exception e) {
            throw new RepositoryOperationException("Error saving course: " + e.getMessage());
        }
    }

    public List<Course> getAllCourses() {
        try {
            return courseRepository.findAll();
        } catch (Exception e) {
            throw new RepositoryOperationException("Error retrieving courses: " + e.getMessage());
        }
    }

    public List<Course> getCoursesByLevel(String level) {
        if (level == null || level.isEmpty()) {
            throw new InvalidLevelException("Level cannot be null or empty.");
        }

        try {
            return courseRepository.findByLevel(level);
        } catch (Exception e) {
            throw new RepositoryOperationException("Error retrieving courses by level: " + e.getMessage());
        }
    }

    public Course getCourseById(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else {
            throw new CourseNotFoundException("Course not found for ID: " + courseId);
        }
    }
}
