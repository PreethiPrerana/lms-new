package com.thbs.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thbs.lms.model.Course;
import com.thbs.lms.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return courses;
    }

    @GetMapping("/level")
    public List<Course> getCoursesByLevel(@RequestParam String level) {
        List<Course> courses = courseService.getCoursesByLevel(level);
        return courses;
    }
}
