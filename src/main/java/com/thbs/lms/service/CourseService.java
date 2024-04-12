package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.Course;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.DTO.CourseDTO;
import com.thbs.lms.DTO.TopicDTO;
import com.thbs.lms.exceptionHandler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicService topicService;

    public Course saveCourse(Course course) {
        if (course.getCourseName() == null || course.getCourseName().isEmpty() ||
                course.getLevel() == null || course.getLevel().isEmpty()) {
            throw new InvalidCourseDataException("Course name, and level cannot be null or empty.");
        }

        Optional<Course> existingCourse = courseRepository.findByCourseName(course.getCourseName());
        if (existingCourse.isPresent()) {
            throw new DuplicateCourseException("Course with name '" + course.getCourseName() + "' already exists.");
        }

        return courseRepository.save(course);
    }

    public List<Course> saveCourses(List<Course> courses) {
        List<Course> savedCourses = new ArrayList<>();

        for (Course course : courses) {
            if (course.getCourseName() == null || course.getCourseName().isEmpty() ||
                    course.getLevel() == null || course.getLevel().isEmpty()) {
                throw new InvalidCourseDataException("Course name, and level cannot be null or empty.");
            }

            Optional<Course> existingCourse = courseRepository.findByCourseName(course.getCourseName());
            if (existingCourse.isPresent()) {
                throw new DuplicateCourseException(
                        "Course with name '" + course.getCourseName() + "' already exists.");
            }
            savedCourses.add(courseRepository.save(course));
        }
        return savedCourses;

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
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else {
            throw new CourseNotFoundException("Course not found for ID: " + courseId);
        }
    }

    public List<CourseDTO> getAllCourseDTOs() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
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
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setCourseName(newCourseName);
            return courseRepository.save(course);
        } else {
            throw new CourseNotFoundException("Course not found for ID: " + courseId);
        }
    }

    public void deleteCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            topicService.deleteTopicsByCourse(course);
            courseRepository.delete(course);
        } else {
            throw new CourseNotFoundException("Course not found for ID: " + courseId);
        }
    }

    public void deleteCourses(List<Course> courses) {
        for (Course course : courses) {
            Long courseId = course.getCourseID();
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (optionalCourse.isPresent()) {
                topicService.deleteTopicsByCourse(course);
                courseRepository.delete(course);
            } else {
                throw new CourseNotFoundException("Course not found for ID: " + courseId);
            }
        }
    }
}
