package com.thbs.lms.service;

import com.thbs.lms.exception.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LearningPlanPathService {

    private static final String NOT_FOUND_MSG = "Learning Plan Path not found for ID: ";
    private LearningPlanPathRepository learningPlanPathRepository;

    @Autowired
    public LearningPlanPathService(LearningPlanPathRepository learningPlanPathRepository) {
        this.learningPlanPathRepository = learningPlanPathRepository;
    }

    private void validateLearningPlanPathData(LearningPlanPath learningPlanPath) {
        if (learningPlanPath.getStartDate() == null || learningPlanPath.getEndDate() == null
                || learningPlanPath.getTrainer() == null || learningPlanPath.getType() == null
                || learningPlanPath.getTrainer().isEmpty() || learningPlanPath.getType().isEmpty()
                || learningPlanPath.getCourse() == null) {
            throw new InvalidLearningPlanPathDataException(
                    "Invalid or incomplete data provided for creating Learning Plan Path");
        }
    }

    private void validateDuplicateLearningPlanPath(LearningPlanPath learningPlanPath) {
        Long learningPlanId = learningPlanPath.getLearningPlan().getLearningPlanID();
        Course course = learningPlanPath.getCourse();
        String type = learningPlanPath.getType();

        if (learningPlanPathRepository.existsByLearningPlanLearningPlanIDAndCourseAndType(learningPlanId, course,
                type)) {
            throw new DuplicateLearningPlanPathException(
                    "A learning plan path with the same course, learning plan ID, and type already exists.");
        }
    }

    public LearningPlanPath saveLearningPlanPath(LearningPlanPath learningPlanPath) {
        validateLearningPlanPathData(learningPlanPath);
        validateDuplicateLearningPlanPath(learningPlanPath);

        return learningPlanPathRepository.save(learningPlanPath);
    }

    public List<LearningPlanPath> saveAllLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        return learningPlanPaths.stream()
                .map(this::saveLearningPlanPath)
                .collect(Collectors.toList());
    }

    public List<LearningPlanPath> getAllLearningPlanPaths() {
        return learningPlanPathRepository.findAll();
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        return learningPlanPathRepository.findByLearningPlanLearningPlanID(learningPlanId);
    }

    private void validateType(String type) {
        if (type == null || type.isEmpty()) {
            throw new InvalidTypeException("Type cannot be null or empty.");
        }
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByType(String type) {
        validateType(type);
        return learningPlanPathRepository.findByType(type);
    }

    private void validateTrainer(String newTrainer) {
        if (newTrainer == null || newTrainer.isEmpty()) {
            throw new InvalidTrainerException("Invalid or Incomplete trainer value provided");
        }
    }

    public List<LearningPlanPath> getAllLearningPlanPathsByTrainer(String trainer) {
        validateTrainer(trainer);
        return learningPlanPathRepository.findByTrainer(trainer);
    }

    private LearningPlanPath getLearningPlanPathById(Long pathId) {
        return learningPlanPathRepository.findById(pathId)
                .orElseThrow(() -> new LearningPlanPathNotFoundException(NOT_FOUND_MSG + pathId));
    }

    public LearningPlanPath updateLearningPlanPathType(Long pathId, String newType) {
        validateType(newType);
        LearningPlanPath learningPlanPath = getLearningPlanPathById(pathId);
        learningPlanPath.setType(newType);
        return learningPlanPathRepository.save(learningPlanPath);
    }

    public LearningPlanPath updateLearningPlanPathTrainer(Long pathId, String newTrainer) {
        validateTrainer(newTrainer);
        LearningPlanPath learningPlanPath = getLearningPlanPathById(pathId);
        learningPlanPath.setTrainer(newTrainer);
        return learningPlanPathRepository.save(learningPlanPath);
    }

    private void validateDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidLearningPlanPathDataException(
                    "Invalid or incomplete date provided for updating Learning Plan Path");
        }

        if (endDate.before(startDate)) {
            throw new InvalidLearningPlanPathDataException("End date must be after start date");
        }
    }

    public Optional<LearningPlanPath> updateLearningPlanPathDates(Long learningPlanPathID, Date startDate,
            Date endDate) {
        validateDates(startDate, endDate);

        return learningPlanPathRepository.findById(learningPlanPathID)
                .map(learningPlanPath -> {
                    learningPlanPath.setStartDate(startDate);
                    learningPlanPath.setEndDate(endDate);
                    return learningPlanPathRepository.save(learningPlanPath);
                })
                .map(Optional::of)
                .orElseThrow(() -> new LearningPlanPathNotFoundException(NOT_FOUND_MSG + learningPlanPathID));
    }

    public void deleteLearningPlanPath(Long learningPlanPathId) {
        learningPlanPathRepository.findById(learningPlanPathId).ifPresentOrElse(
                learningPlanPathRepository::delete,
                () -> {
                    throw new LearningPlanPathNotFoundException(NOT_FOUND_MSG + learningPlanPathId);
                });
    }

    public void deleteLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        List<LearningPlanPath> learningPlanPaths = learningPlanPathRepository
                .findByLearningPlanLearningPlanID(learningPlanId);

        learningPlanPaths.stream()
                .map(LearningPlanPath::getPathID)
                .forEach(this::deleteLearningPlanPath);
    }

    public void deleteLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        learningPlanPathRepository.deleteAll(learningPlanPaths);
    }
}
