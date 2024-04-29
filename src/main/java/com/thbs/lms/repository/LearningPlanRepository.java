package com.thbs.lms.repository;

import com.thbs.lms.model.LearningPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The {@code LearningPlanRepository} interface provides CRUD operations for the
 * {@link com.thbs.lms.model.LearningPlan} entity.
 */
@Repository
public interface LearningPlanRepository extends JpaRepository<LearningPlan, Long> {
    /**
     * Retrieves a list of learning plans by their type.
     *
     * @param type The type of the learning plans to retrieve.
     * @return A list of learning plans with the specified type.
     */
    List<LearningPlan> findByType(String type);

    /**
     * Retrieves a list of learning plans by their batch ID.
     *
     * @param batchId The ID of the batch associated with the learning plans.
     * @return A list of learning plans with the specified batch ID.
     */
    List<LearningPlan> findByBatchId(Long batchId);
}
