package com.thbs.lms.repository;

import org.springframework.stereotype.Repository;

import com.thbs.lms.model.Course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@code CourseRepository} interface provides CRUD operations for the
 * {@link com.thbs.lms.model.Course} entity.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    /**
     * Retrieves a list of courses by their level.
     *
     * @param level The level of the courses to retrieve.
     * @return A list of courses with the specified level.
     */
    List<Course> findByLevel(String level);

    /**
     * Retrieves a course by its name, ignoring case sensitivity.
     *
     * @param courseName The name of the course to retrieve.
     * @return An optional containing the course with the specified name, if found.
     */
    Optional<Course> findByCourseNameIgnoreCase(String courseName);

    /**
     * Saves the specified course.
     *
     * @param course The course to save.
     * @return The saved course.
     */
    Object save(Optional<Course> course);
}
