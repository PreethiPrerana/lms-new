package com.thbs.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The {@code Course} class represents a course in the learning management
 * system.
 * It contains information such as the course ID, name, and level.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Course")
public class Course {
    /**
     * The unique identifier for the course.
     */
    @Id
    @GeneratedValue(generator = "sequence-generator-course")
    @GenericGenerator(name = "sequence-generator-course", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "initial_value", value = "100"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long courseId;

    /**
     * The name of the course.
     */
    private String courseName;

    /**
     * The level of the course.
     */
    private String level;
}
