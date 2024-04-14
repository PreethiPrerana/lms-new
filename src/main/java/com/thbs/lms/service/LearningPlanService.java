package com.thbs.lms.service;

import com.thbs.lms.exception.*;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.repository.LearningPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningPlanService {

    private final LearningPlanRepository learningPlanRepository;
    private final LearningPlanPathService learningPlanPathService;

    @Autowired
    public LearningPlanService(LearningPlanPathService learningPlanPathService,
            LearningPlanRepository learningPlanRepository) {
        this.learningPlanPathService = learningPlanPathService;
        this.learningPlanRepository = learningPlanRepository;
    }

    public LearningPlan saveLearningPlan(LearningPlan learningPlan) {
        List<LearningPlan> existingLearningPlans = learningPlanRepository.findByBatchID(learningPlan.getBatchID());
        if (!existingLearningPlans.isEmpty()) {
            throw new DuplicateLearningPlanException(
                    "Learning plan for this batch " + learningPlan.getBatchID() + " already exists.");
        }
        if (learningPlan.getBatchID() == null || learningPlan.getType() == null || learningPlan.getType().isEmpty()) {
            throw new InvalidLearningPlanException("Batch ID or LearningPlan Type cannot be null");
        }
        return learningPlanRepository.save(learningPlan);
    }

    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    public LearningPlan getLearningPlanById(Long id) {
        return learningPlanRepository.findById(id)
                .orElseThrow(() -> new LearningPlanNotFoundException("Learning plan with ID " + id + " not found."));
    }

    public List<LearningPlan> getLearningPlansByType(String type) {
        if (type == null || type.isEmpty()) {
            throw new InvalidTypeException("LearningPlan Type cannot be null");
        }
        List<LearningPlan> learningPlans = learningPlanRepository.findByType(type);
        if (learningPlans.isEmpty()) {
            throw new LearningPlanNotFoundException("Learning plan with Type " + type + " not found.");
        }
        return learningPlans;
    }

    public List<LearningPlan> getLearningPlansByBatchID(Long batchID) {
        if (batchID == null) {
            throw new InvalidBatchException("LearningPlan Type cannot be null");
        }
        List<LearningPlan> learningPlans = learningPlanRepository.findByBatchID(batchID);
        if (learningPlans.isEmpty()) {
            throw new LearningPlanNotFoundException("Learning plan for Batch " + batchID + " not found.");
        }
        return learningPlans;
    }

    public void deleteLearningPlan(Long id) {
        LearningPlan learningPlan = getLearningPlanById(id);
        learningPlanPathService.deleteLearningPlanPathsByLearningPlanId(id);
        learningPlanRepository.delete(learningPlan);
    }
}
