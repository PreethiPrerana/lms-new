package com.thbs.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code LearningPlan} class represents a learning plan in the learning
 * management system.
 * It contains information such as the learning plan ID, type, and batch ID.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LearningPlan")
public class LearningPlan {
    /**
     * The unique identifier for the learning plan.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learningPlanId;

    /**
     * The type of the learning plan.
     */
    private String type;

    /**
     * The batch ID associated with the learning plan.
     */
    private Long batchId;
}
