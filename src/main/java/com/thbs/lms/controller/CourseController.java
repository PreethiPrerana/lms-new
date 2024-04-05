package com.thbs.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.model.Course;
import com.thbs.lms.service.CourseService;
import com.thbs.lms.exceptionHandler.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        try {
            Course addedCourse = courseService.saveCourse(course);
            return ResponseEntity.ok().body(addedCourse);
        } catch (InvalidCourseDataException | DuplicateCourseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            return ResponseEntity.ok().body(courses);
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/level")
    public ResponseEntity<?> getCoursesByLevel(@RequestParam String level) {
        try {
            List<Course> courses = courseService.getCoursesByLevel(level);
            return ResponseEntity.ok().body(courses);
        } catch (InvalidLevelException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getCourseById(@RequestParam Long id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok().body(course);
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
