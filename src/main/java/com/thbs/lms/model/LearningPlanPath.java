package com.thbs.lms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "LearningPlanPath")
public class LearningPlanPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pathID;

    @ManyToOne
    @JoinColumn(name = "learningPlanID")
    private LearningPlan learningPlan;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;

    private String type;

    private String trainer;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;
}