package com.thbs.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The {@code LearningPlanPath} class represents a path within a learning plan
 * in the learning management system.
 * It contains information such as the path ID, associated learning plan,
 * course, type, trainer, start date, and end date.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LearningPlanPath")
public class LearningPlanPath {
    /**
     * The unique identifier for the learning plan path.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pathId;

    /**
     * The learning plan associated with the path.
     */
    @ManyToOne
    @JoinColumn(name = "learningPlanId")
    private LearningPlan learningPlan;

    /**
     * The course associated with the path.
     */
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    /**
     * The type of the path.
     */
    private String type;

    /**
     * The trainer responsible for the path.
     */
    private String trainer;

    /**
     * The start date of the path.
     */
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * The end date of the path.
     */
    @Temporal(TemporalType.DATE)
    private Date endDate;
}
