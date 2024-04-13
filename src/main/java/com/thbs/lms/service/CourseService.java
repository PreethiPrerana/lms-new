package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.Course;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.DTO.CourseDTO;
import com.thbs.lms.DTO.TopicDTO;
import com.thbs.lms.exception.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private static final String NOT_FOUND_MSG = "Course not found for ID: ";
    private CourseRepository courseRepository;
    private TopicService topicService;

    @Autowired
    public CourseService(CourseRepository courseRepository, TopicService topicService) {
        this.courseRepository = courseRepository;
        this.topicService = topicService;
    }

    private void validateCourseData(Course course) {
        if (course.getCourseName() == null || course.getCourseName().isEmpty() ||
                course.getLevel() == null || course.getLevel().isEmpty()) {
            throw new InvalidCourseDataException("Course name, and level cannot be null or empty.");
        }
    }

    private void checkDuplicateCourse(String courseName) {
        courseRepository.findByCourseName(courseName)
                .ifPresent(existingCourse -> {
                    throw new DuplicateCourseException("Course with name '" + courseName + "' already exists.");
                });
    }

    private Course validateAndCheckDuplicateCourse(Course course) {
        validateCourseData(course);
        checkDuplicateCourse(course.getCourseName());
        return courseRepository.save(course);
    }

    public Course saveCourse(Course course) {
        return validateAndCheckDuplicateCourse(course);
    }

    public List<Course> saveCourses(List<Course> courses) {
        return courses.stream()
                .map(this::validateAndCheckDuplicateCourse)
                .collect(Collectors.toList());
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByLevel(String level) {
        if (level == null || level.isEmpty()) {
            throw new InvalidLevelException("Level cannot be null or empty.");
        }
        return courseRepository.findByLevel(level);
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_MSG + courseId));
    }

    public List<CourseDTO> getAllCourseDTOs() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CourseDTO convertToDTO(Course course) {
        List<TopicDTO> topicDTOs = topicService.getTopicsByCourse(course)
                .stream()
                .map(topic -> new TopicDTO(topic.getTopicID(), topic.getTopicName()))
                .collect(Collectors.toList());

        return new CourseDTO(course.getCourseID(), course.getCourseName(), topicDTOs);
    }

    public Course updateCourseName(Long courseId, String newCourseName) {
        Course course = getCourseById(courseId);
        course.setCourseName(newCourseName);
        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        Course course = getCourseById(courseId);
        topicService.deleteTopicsByCourse(course);
        courseRepository.delete(course);
    }

    public void deleteCourses(List<Course> courses) {
        courses.forEach(course -> {
            Long courseId = course.getCourseID();
            Course existingCourse = getCourseById(courseId);
            topicService.deleteTopicsByCourse(existingCourse);
            courseRepository.delete(existingCourse);
        });
    }
}
