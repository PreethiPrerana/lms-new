package com.thbs.lms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code PathDTO} class represents a data transfer object (DTO) for a
 * learning plan path.
 * It encapsulates information about a learning plan path, including its ID,
 * type, trainer, start date, end date, and associated course.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathDTO {

    /**
     * The ID of the learning plan path.
     */
    private Long learningPlanPathId;

    /**
     * The type of the learning plan path (e.g., Course, Module).
     */
    private String type;

    /**
     * The trainer associated with the learning plan path.
     */
    private String trainer;

    /**
     * The start date of the learning plan path.
     */
    private Date startDate;

    /**
     * The end date of the learning plan path.
     */
    private Date endDate;

    /**
     * The course associated with the learning plan path.
     */
    private CourseDTO course;
}
