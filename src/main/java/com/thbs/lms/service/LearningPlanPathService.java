package com.thbs.lms.service;

import com.thbs.lms.exceptionHandler.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanPathService {

    @Autowired
    private LearningPlanPathRepository learningPlanPathRepository;

    public LearningPlanPath saveLearningPlanPath(LearningPlanPath learningPlanPath) {
        if (learningPlanPath.getStartDate() == null || learningPlanPath.getEndDate() == null
                || learningPlanPath.getTrainer() == null || learningPlanPath.getType() == null
                || learningPlanPath.getTrainer().isEmpty() || learningPlanPath.getType().isEmpty()
                || learningPlanPath.getCourse() == null) {
            throw new InvalidLearningPlanPathDataException(
                    "Invalid or incomplete data provided for creating Learning Plan Path");
        }
        Long learningPlanId = learningPlanPath.getLearningPlan().getLearningPlanID();
        Course course = learningPlanPath.getCourse();
        String type = learningPlanPath.getType();

        Optional<LearningPlanPath> existingEntry = learningPlanPathRepository
                .findByLearningPlanLearningPlanIDAndCourseAndType(learningPlanId, course, type);
        if (existingEntry.isPresent()) {
            throw new DuplicateLearningPlanPathException(
                    "A learning plan path with the same course, learning plan ID, and type already exists.");
        }

        return learningPlanPathRepository.save(learningPlanPath);
    }

    public List<LearningPlanPath> saveAllLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        List<LearningPlanPath> savedPaths = new ArrayList<LearningPlanPath>();
        for (LearningPlanPath learningPlanPath : learningPlanPaths) {
            savedPaths.add(saveLearningPlanPath(learningPlanPath));
        }
        return savedPaths;
    }

    public List<LearningPlanPath> getAllLearningPlanPaths() {
        return learningPlanPathRepository.findAll();
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        return learningPlanPathRepository.findByLearningPlanLearningPlanID(learningPlanId);
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByType(String type) {
        if (type == null || type.isEmpty()) {
            throw new InvalidTypeException("Type cannot be null or empty.");
        }

        return learningPlanPathRepository.findByType(type);
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByTrainer(String trainer) {
        if (trainer == null || trainer.isEmpty()) {
            throw new InvalidTrainerException("trainer cannot be null or empty.");
        }
        return learningPlanPathRepository.findByTrainer(trainer);
    }

    public LearningPlanPath updateLearningPlanPathTrainer(Long pathId, String newTrainer) {
        if (newTrainer == null || newTrainer.isEmpty()) {
            throw new InvalidTrainerException("Invalid or Incomplete trainer value provided");
        }
        LearningPlanPath learningPlanPath = learningPlanPathRepository.findById(pathId)
                .orElseThrow(() -> new LearningPlanPathNotFoundException(
                        "Learning Plan Path not found for ID: " + pathId));
        learningPlanPath.setTrainer(newTrainer);
        return learningPlanPathRepository.save(learningPlanPath);
    }

    public Optional<LearningPlanPath> updateLearningPlanPathDates(Long learningPlanPathID, Date startDate,
            Date endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidLearningPlanPathDataException(
                    "Invalid or incomplete date provided for updating Learning Plan Path");
        }

        if (endDate.before(startDate)) {
            throw new InvalidLearningPlanPathDataException("End date must be after start date");
        }

        Optional<LearningPlanPath> optionalLearningPlanPath = learningPlanPathRepository
                .findById(learningPlanPathID);
        if (optionalLearningPlanPath.isPresent()) {
            LearningPlanPath learningPlanPath = optionalLearningPlanPath.get();
            learningPlanPath.setStartDate(startDate);
            learningPlanPath.setEndDate(endDate);
            return Optional.of(learningPlanPathRepository.save(learningPlanPath));
        } else {
            throw new LearningPlanPathNotFoundException(
                    "Learning Plan Path not found for ID: " + learningPlanPathID);
        }
    }

    public void deleteLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        List<LearningPlanPath> learningPlanPaths = learningPlanPathRepository
                .findByLearningPlanLearningPlanID(learningPlanId);
        for (LearningPlanPath path : learningPlanPaths) {
            deleteLearningPlanPath(path.getPathID());
        }
    }

    public void deleteLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        learningPlanPathRepository.deleteAll(learningPlanPaths);
    }

    public void deleteLearningPlanPath(Long learningPlanPathId) {

        Optional<LearningPlanPath> learningPlanPath = learningPlanPathRepository.findById(learningPlanPathId);
        if (learningPlanPath.isPresent()) {
            learningPlanPathRepository.delete(learningPlanPath.get());
        } else {
            throw new LearningPlanPathNotFoundException(
                    "Learning Plan Path not found for ID: " + learningPlanPathId);
        }
    }
}
