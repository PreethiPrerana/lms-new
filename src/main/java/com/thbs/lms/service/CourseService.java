package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.Course;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.dto.TopicDTO;
import com.thbs.lms.exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code CourseService} class provides methods for managing courses and
 * their associated topics.
 */
@Service
public class CourseService {

    private static final String NOT_FOUND_MSG = "Course not found for ID: ";
    private CourseRepository courseRepository;
    private TopicService topicService;

    /**
     * Constructs a new instance of {@code CourseService} with the specified
     * repositories.
     *
     * @param courseRepository The repository for managing courses.
     * @param topicService     The service for managing topics.
     */
    @Autowired
    public CourseService(CourseRepository courseRepository, TopicService topicService) {
        this.courseRepository = courseRepository;
        this.topicService = topicService;
    }

    /**
     * Saves a single course to the database.
     *
     * @param course The course to be saved.
     * @return The saved course.
     * @throws InvalidCourseDataException If the course data is invalid.
     * @throws DuplicateCourseException   If a course with the same name already
     *                                    exists.
     */
    public Course saveCourse(Course course) {
        // Validation checks
        if (course.getCourseName() == null || course.getCourseName().isEmpty() ||
                course.getLevel() == null || course.getLevel().isEmpty()) {
            // Throws exceptions for invalid data or duplicate course
            throw new InvalidCourseDataException("Course name, and level cannot be null or empty.");
        }

        Optional<Course> existingCourse = courseRepository.findByCourseNameIgnoreCase(course.getCourseName());
        if (existingCourse.isPresent()) {
            throw new DuplicateCourseException("Course with name '" + course.getCourseName() + "' already exists.");
        }

        return courseRepository.save(course);
    }

    /**
     * Saves multiple courses to the database.
     *
     * @param courses The list of courses to be saved.
     * @return The list of saved courses.
     * @throws InvalidCourseDataException If any of the course data is invalid.
     * @throws DuplicateCourseException   If a course with the same name already
     *                                    exists.
     */
    public List<Course> saveCourses(List<Course> courses) {
        List<Course> savedCourses = new ArrayList<>();
        // Validation checks
        for (Course course : courses) {
            if (course.getCourseName() == null || course.getCourseName().isEmpty() ||
                    course.getLevel() == null || course.getLevel().isEmpty()) {
                // Throws exceptions for invalid data or duplicate course
                throw new InvalidCourseDataException("Course name, and level cannot be null or empty.");
            }

            Optional<Course> existingCourse = courseRepository.findByCourseNameIgnoreCase(course.getCourseName());
            if (existingCourse.isPresent()) {
                throw new DuplicateCourseException(
                        "Course with name '" + course.getCourseName() + "' already exists.");
            }
            savedCourses.add(courseRepository.save(course));
        }
        return savedCourses;

    }

    /**
     * Retrieves all courses from the database.
     *
     * @return The list of all courses.
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Retrieves courses by their level from the database.
     *
     * @param level The level of the courses to retrieve.
     * @return The list of courses with the specified level.
     * @throws InvalidLevelException If the level is invalid.
     */
    public List<Course> getCoursesByLevel(String level) {
        // Validation checks for level

        if (level == null || level.isEmpty()) {
            throw new InvalidLevelException("Level cannot be null or empty.");
        }
        return courseRepository.findByLevel(level);
    }

    /**
     * Retrieves a course by its ID from the database.
     *
     * @param courseId The ID of the course to retrieve.
     * @return The course with the specified ID.
     * @throws CourseNotFoundException If the course with the specified ID is not
     *                                 found.
     */
    public Course getCourseById(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        // Retrieves a course by ID or throws exception if not found
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else {
            throw new CourseNotFoundException(NOT_FOUND_MSG + courseId);
        }
    }

    /**
     * Retrieves all courses as DTOs (Data Transfer Objects) from the database.
     *
     * @return The list of course DTOs.
     */
    public List<CourseDTO> getAllCourseDTOs() {
        // Converts each course to DTO and returns a list of DTOs
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a course entity to a DTO (Data Transfer Object).
     *
     * @param course The course entity to convert.
     * @return The course DTO.
     */
    public CourseDTO convertToDTO(Course course) {
        // Converts course entity to DTO format with associated topics
        List<TopicDTO> topicDTOs = topicService.getTopicsByCourse(course)
                .stream()
                .map(topic -> new TopicDTO(topic.getTopicID(), topic.getTopicName()))
                .collect(Collectors.toList());

        return new CourseDTO(course.getCourseId(), course.getCourseName(), topicDTOs);
    }

    /**
     * Updates the name of the course with the given ID in the database.
     *
     * @param courseId      The ID of the course to update.
     * @param newCourseName The new name for the course.
     * @return The updated course.
     * @throws CourseNotFoundException If the course with the specified ID is not
     *                                 found.
     */
    public Course updateCourseName(Long courseId, String newCourseName) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setCourseName(newCourseName);
            return courseRepository.save(course);
        } else {
            throw new CourseNotFoundException(NOT_FOUND_MSG + courseId);
        }
    }

    /**
     * Deletes a course by its ID from the database along with its associated
     * topics.
     *
     * @param courseId The ID of the course to delete.
     * @throws CourseNotFoundException If the course with the specified ID is not
     *                                 found.
     */
    public void deleteCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            topicService.deleteTopicsByCourse(course);
            courseRepository.delete(course);
        } else {
            throw new CourseNotFoundException(NOT_FOUND_MSG + courseId);
        }
    }

    /**
     * Deletes multiple courses by their IDs from the database along with their
     * associated topics.
     *
     * @param courses The list of courses to delete.
     * @throws CourseNotFoundException If any of the courses with the specified IDs
     *                                 are not found.
     */
    public void deleteCourses(List<Course> courses) {
        for (Course course : courses) {
            Long courseId = course.getCourseId();
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (optionalCourse.isPresent()) {
                topicService.deleteTopicsByCourse(course);
                courseRepository.delete(course);
            } else {
                throw new CourseNotFoundException(NOT_FOUND_MSG + courseId);
            }
        }
    }
}
