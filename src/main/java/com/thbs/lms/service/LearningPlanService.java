package com.thbs.lms.service;

import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.repository.LearningPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningPlanService {

    @Autowired
    private LearningPlanRepository learningPlanRepository;

    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    public LearningPlan saveLearningPlan(LearningPlan learningPlan) {
        return learningPlanRepository.save(learningPlan);
    }

    public List<LearningPlan> findByType(String type) {
        return learningPlanRepository.findByType(type);
    }

    public List<LearningPlan> findByBatchID(Long batchID) {
        return learningPlanRepository.findByBatchID(batchID);
    }
}
