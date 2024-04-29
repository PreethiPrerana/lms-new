package com.thbs.lms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code CourseDTO} class represents a data transfer object (DTO) for a
 * course.
 * It encapsulates information about a course, including its ID, name, and
 * associated topics.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    /**
     * The ID of the course.
     */
    private Long courseId;

    /**
     * The name of the course.
     */
    private String courseName;

    /**
     * The list of topics associated with the course.
     */
    private List<TopicDTO> topics;
}
