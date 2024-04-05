package com.thbs.lms.repository;

import org.springframework.stereotype.Repository;

import com.thbs.lms.model.Course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseName(String courseName);

    List<Course> findByLevel(String level);

}
