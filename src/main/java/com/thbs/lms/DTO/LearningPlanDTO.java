package com.thbs.lms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code LearningPlanDTO} class represents a data transfer object (DTO) for
 * a learning plan.
 * It encapsulates information about a learning plan, including its batch ID,
 * learning plan ID, and associated paths.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearningPlanDTO {

    /**
     * The batch ID of the learning plan.
     */
    private Long batchId;

    /**
     * The ID of the learning plan.
     */
    private Long learningPlanId;

    /**
     * The list of paths associated with the learning plan.
     */
    private List<PathDTO> path;
}
