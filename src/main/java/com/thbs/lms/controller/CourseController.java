package com.thbs.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.model.Course;
import com.thbs.lms.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        Course addedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok().body(addedCourse);
    }

    @PostMapping("/addAll")
    public ResponseEntity<?> addCourses(@RequestBody List<Course> courses) {
        List<Course> addedCourses = courseService.saveCourses(courses);
        return ResponseEntity.ok().body(addedCourses);
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/level")
    public ResponseEntity<?> getCoursesByLevel(@RequestParam String level) {
        List<Course> courses = courseService.getCoursesByLevel(level);
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getCourseById(@RequestParam Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok().body(course);
    }

    // @DeleteMapping
    // public ResponseEntity<?> deleteCourses(@RequestBody List<Course> courses) {
    //     courseService.deleteCourses(courses);
    //     return ResponseEntity.ok().body("Courses deleted successfully");
    // }
}
