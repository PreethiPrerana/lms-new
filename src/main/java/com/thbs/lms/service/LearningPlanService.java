package com.thbs.lms.service;

import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.repository.LearningPlanRepository;
import com.thbs.lms.exceptionHandler.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanService {

    @Autowired
    private LearningPlanRepository learningPlanRepository;

    @Autowired
    private LearningPlanPathService learningPlanPathService;

    public LearningPlan saveLearningPlan(LearningPlan learningPlan) {
        List<LearningPlan> existingLearningPlan = learningPlanRepository.findByBatchID(learningPlan.getBatchID());
        if (!existingLearningPlan.isEmpty()) {
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
        Optional<LearningPlan> optionalLearningPlan = learningPlanRepository.findById(id);
        if (optionalLearningPlan.isPresent()) {
            return optionalLearningPlan.get();
        } else {
            throw new LearningPlanNotFoundException("Learning plan with ID " + id + " not found.");
        }
    }

    public List<LearningPlan> getLearningPlansByType(String type) {
        if (type == null || type.isEmpty()) {
            throw new InvalidTypeException("LearningPlan Type cannot be null");
        }
        List<LearningPlan> learningPlan = learningPlanRepository.findByType(type);
        if (!learningPlan.isEmpty()) {
            return learningPlan;
        } else {
            throw new LearningPlanNotFoundException("Learning plan with Type " + type + " not found.");
        }
    }

    public List<LearningPlan> getLearningPlansByBatchID(Long batchID) {
        if (batchID == null) {
            throw new InvalidBatchException("LearningPlan Type cannot be null");
        }
        List<LearningPlan> learningPlan = learningPlanRepository.findByBatchID(batchID);
        if (!learningPlan.isEmpty()) {
            return learningPlan;
        } else {
            throw new LearningPlanNotFoundException("Learning plan for Batch " + batchID + " not found.");
        }
    }

    public void deleteLearningPlan(Long id) {
        Optional<LearningPlan> optionalLearningPlan = learningPlanRepository.findById(id);
        if (optionalLearningPlan.isPresent()) {
            learningPlanPathService.deleteLearningPlanPathsByLearningPlanId(id);
            learningPlanRepository.delete(optionalLearningPlan.get());
        } else {
            throw new LearningPlanNotFoundException("Learning plan with ID " + id + " not found.");
        }
    }
}
