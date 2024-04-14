package com.thbs.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.model.Course;
import com.thbs.lms.service.CourseService;

@RestController
@RequestMapping("/course")
@CrossOrigin("*")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course addedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok().body(addedCourse);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/dto")
    public ResponseEntity<List<CourseDTO>> getAllCourseDTOs() {
        List<CourseDTO> courseDTOs = courseService.getAllCourseDTOs();
        return ResponseEntity.ok().body(courseDTOs);
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<Course>> getCoursesByLevel(@PathVariable String level) {
        List<Course> courses = courseService.getCoursesByLevel(level);
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok().body(course);
    }

    @PatchMapping("/name/{id}")
    public ResponseEntity<Course> updateCourseName(@PathVariable Long id, @RequestBody String newCourseName) {
        Course updatedCourse = courseService.updateCourseName(id, newCourseName);
        return ResponseEntity.ok().body(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().body("Course deleted successfully");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteCourses(@RequestBody List<Course> courses) {
        courseService.deleteCourses(courses);
        return ResponseEntity.ok().body("Courses deleted successfully");
    }
}
