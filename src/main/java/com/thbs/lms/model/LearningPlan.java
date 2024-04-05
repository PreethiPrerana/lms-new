package com.thbs.lms.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "LearningPlan")
public class LearningPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learningPlanID;

    private String type;

    private Long batchID;
}