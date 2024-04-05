package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Optional<LearningPlanPath> updateDates(Long learningPlanPathID, Date startDate, Date endDate) {
        Optional<LearningPlanPath> optionalLearningPlanPath = learningPlanPathRepository.findById(learningPlanPathID);
        if (optionalLearningPlanPath.isPresent()) {
            LearningPlanPath learningPlanPath = optionalLearningPlanPath.get();
            learningPlanPath.setStartDate(startDate);
            learningPlanPath.setEndDate(endDate);
            return Optional.of(learningPlanPathRepository.save(learningPlanPath));
        } else {
            return Optional.empty();
        }
    }
}
