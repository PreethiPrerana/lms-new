package com.thbs.lms.repository;

import org.springframework.stereotype.Repository;

import com.thbs.lms.model.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByLevel(String level);

}
