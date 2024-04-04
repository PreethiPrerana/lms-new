package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;

import java.util.Date;
import java.util.List;

@Service
public class LearningPlanPathService {

    @Autowired
    private LearningPlanPathRepository learningPlanPathRepository;

    public LearningPlanPath createLearningPlanPath(LearningPlanPath learningPlanPath) {
       return learningPlanPathRepository.save(learningPlanPath);
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        return learningPlanPathRepository.findByLearningPlanLearningPlanID(learningPlanId);
    }

    public List<LearningPlanPath> getAllLearningPlansByType(String type) {
        return learningPlanPathRepository.findByType(type);
    }

    public List<LearningPlanPath> getAllLearningPlansByTrainer(String trainer) {
        return learningPlanPathRepository.findByTrainer(trainer);
    }

    public void updateTrainer(Long pathId, String newTrainer) {
        LearningPlanPath learningPlanPath = learningPlanPathRepository.findById(pathId)
                .orElseThrow(() -> new IllegalArgumentException("LearningPlanPath not found for ID: " + pathId));
        learningPlanPath.setTrainer(newTrainer);
        learningPlanPathRepository.save(learningPlanPath);
    }

    public void updateDates(Long pathId, Date newStartDate, Date newEndDate) {
        LearningPlanPath learningPlanPath = learningPlanPathRepository.findById(pathId)
                .orElseThrow(() -> new IllegalArgumentException("LearningPlanPath not found for ID: " + pathId));
        learningPlanPath.setStartDate(newStartDate);
        learningPlanPath.setEndDate(newEndDate);
        learningPlanPathRepository.save(learningPlanPath);
    }
}
