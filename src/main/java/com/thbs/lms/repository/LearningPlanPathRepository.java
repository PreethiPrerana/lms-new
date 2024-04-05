package com.thbs.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thbs.lms.model.LearningPlanPath;

import java.util.List;

@Repository
public interface LearningPlanPathRepository extends JpaRepository<LearningPlanPath, Long> {

    List<LearningPlanPath> findByLearningPlanLearningPlanID(Long learningPlanID);

    List<LearningPlanPath> findByType(String type);

    List<LearningPlanPath> findByTrainer(String trainer);
}
