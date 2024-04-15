package com.thbs.lms.service;

import com.thbs.lms.exception.*;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.repository.LearningPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanService {

    private LearningPlanRepository learningPlanRepository;
    private LearningPlanPathService learningPlanPathService;

    @Autowired
    public LearningPlanService(LearningPlanPathService learningPlanPathService,
            LearningPlanRepository learningPlanRepository) {
        this.learningPlanPathService = learningPlanPathService;
        this.learningPlanRepository = learningPlanRepository;
    }

    // Saves a new learning plan
    public LearningPlan saveLearningPlan(LearningPlan learningPlan) {
        List<LearningPlan> existingLearningPlan = learningPlanRepository.findByBatchID(learningPlan.getBatchID());
        // Checks if a learning plan already exists for the given batch
        if (!existingLearningPlan.isEmpty()) {
            // Throws exception if duplicate learning plan or invalid data
            throw new DuplicateLearningPlanException(
                    "Learning plan for this batch " + learningPlan.getBatchID() + " already exists.");
        }
        if (learningPlan.getBatchID() == null || learningPlan.getType() == null || learningPlan.getType().isEmpty()) {
            throw new InvalidLearningPlanException("Batch ID or LearningPlan Type cannot be null");
        }
        return learningPlanRepository.save(learningPlan);
    }

    // Retrieves all learning plans
    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    // Retrieves a learning plan by its ID or throws exception if not found
    public LearningPlan getLearningPlanById(Long id) {
        Optional<LearningPlan> optionalLearningPlan = learningPlanRepository.findById(id);
        if (optionalLearningPlan.isPresent()) {
            return optionalLearningPlan.get();
        } else {
            throw new LearningPlanNotFoundException("Learning plan with ID " + id + " not found.");
        }
    }

    // Retrieves learning plans by type
    public List<LearningPlan> getLearningPlansByType(String type) {
        // Validates type and retrieves learning plans by type
        if (type == null || type.isEmpty()) {
            throw new InvalidTypeException("LearningPlan Type cannot be null");
        }
        List<LearningPlan> learningPlan = learningPlanRepository.findByType(type);
        if (!learningPlan.isEmpty()) {
            return learningPlan;
        } else {
            // Throws exception if type is invalid or no learning plans found
            throw new LearningPlanNotFoundException("Learning plan with Type " + type + " not found.");
        }
    }

    // Retrieves learning plans by batch ID
    public List<LearningPlan> getLearningPlansByBatchID(Long batchID) {
        // Validates batch ID and retrieves learning plans by batch ID
        if (batchID == null) {
            // Throws exception if batch ID is invalid or no learning plans found
            throw new InvalidBatchException("LearningPlan Type cannot be null");
        }
        List<LearningPlan> learningPlan = learningPlanRepository.findByBatchID(batchID);
        if (!learningPlan.isEmpty()) {
            return learningPlan;
        } else {
            throw new LearningPlanNotFoundException("Learning plan for Batch " + batchID + " not found.");
        }
    }

    // Deletes the learning plan by ID along with associated paths
    public void deleteLearningPlan(Long id) {
        Optional<LearningPlan> optionalLearningPlan = learningPlanRepository.findById(id);
        if (optionalLearningPlan.isPresent()) {
            // Throws exception if learning plan with given ID is not found
            learningPlanPathService.deleteLearningPlanPathsByLearningPlanId(id);
            learningPlanRepository.delete(optionalLearningPlan.get());
        } else {
            throw new LearningPlanNotFoundException("Learning plan with ID " + id + " not found.");
        }
    }
}
