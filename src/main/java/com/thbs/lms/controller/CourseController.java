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

    @PostMapping("/add")
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

    @GetMapping("/level/{level}")
    public ResponseEntity<?> getCoursesByLevel(@PathVariable String level) {
        List<Course> courses = courseService.getCoursesByLevel(level);
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok().body(course);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<?> updateCourseName(@PathVariable Long id, @RequestBody String newCourseName) {
        Course updatedCourse = courseService.updateCourseName(id, newCourseName);
        return ResponseEntity.ok().body(updatedCourse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().body("Course deleted successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCourses(@RequestBody List<Course> courses) {
        courseService.deleteCourses(courses);
        return ResponseEntity.ok().body("Courses deleted successfully");
    }
}
