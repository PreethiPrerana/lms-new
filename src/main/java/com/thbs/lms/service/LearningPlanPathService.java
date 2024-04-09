package com.thbs.lms.service;

import com.thbs.lms.exceptionHandler.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanPathService {

    @Autowired
    private LearningPlanPathRepository learningPlanPathRepository;

    public LearningPlanPath createLearningPlanPath(LearningPlanPath learningPlanPath) {
        try {
            if (learningPlanPath.getStartDate() == null || learningPlanPath.getEndDate() == null
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

            // If no duplicate entry found, save the learning plan path
            return learningPlanPathRepository.save(learningPlanPath);
        } catch (Exception ex) {
            throw new RepositoryOperationException(
                    "Error occurred while creating Learning Plan Path: " + ex.getMessage());
        }
    }

    public List<LearningPlanPath> saveAllLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        return learningPlanPathRepository.saveAll(learningPlanPaths);
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        try {
            return learningPlanPathRepository.findByLearningPlanLearningPlanID(learningPlanId);
        } catch (Exception e) {
            throw new RepositoryOperationException(
                    "Error retrieving learning plan paths by LearningPlan Id:" + e.getMessage());
        }
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByType(String type) {
        if (type == null || type.isEmpty()) {
            throw new InvalidTypeException("Type cannot be null or empty.");
        }
        try {
            return learningPlanPathRepository.findByType(type);
        } catch (Exception e) {
            throw new RepositoryOperationException("Error retrieving learning plan paths by type :" + e.getMessage());
        }
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByTrainer(String trainer) {
        if (trainer == null || trainer.isEmpty()) {
            throw new InvalidTrainerException("trainer cannot be null or empty.");
        }
        try {
            return learningPlanPathRepository.findByTrainer(trainer);
        } catch (Exception e) {
            throw new RepositoryOperationException("Error occurred while updating Trainer:/ " + e.getMessage());
        }
    }

    public void updateLearningPlanPathTrainer(Long pathId, String newTrainer) {
        try {
            if (newTrainer == null || newTrainer.isEmpty()) {
                throw new InvalidTrainerException("Invalid or Incomplete trainer value provided");
            }
            LearningPlanPath learningPlanPath = learningPlanPathRepository.findById(pathId)
                    .orElseThrow(() -> new LearningPlanPathNotFoundException(
                            "Learning Plan Path not found for ID: " + pathId));
            learningPlanPath.setTrainer(newTrainer);
            learningPlanPathRepository.save(learningPlanPath);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Error occurred while updating Trainer: " + ex.getMessage());
        }
    }

    public Optional<LearningPlanPath> updateLearningPlanPathDates(Long learningPlanPathID, Date startDate, Date endDate) {
        try {
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
        } catch (Exception ex) {
            throw new RepositoryOperationException("Error occurred while updating dates: " + ex.getMessage());
        }
    }
}
