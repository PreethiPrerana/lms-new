package com.thbs.lms.service;

import com.thbs.lms.exception.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The {@code LearningPlanPathService} class provides methods for managing
 * learning plan paths.
 */
@Service
public class LearningPlanPathService {

    private static final String NOT_FOUND_MSG = "Learning Plan Path not found for ID: ";
    private LearningPlanPathRepository learningPlanPathRepository;

    /**
     * Constructs a new instance of {@code LearningPlanPathService} with the
     * specified repository.
     *
     * @param learningPlanPathRepository The repository for managing learning plan
     *                                   paths.
     */
    @Autowired
    public LearningPlanPathService(LearningPlanPathRepository learningPlanPathRepository) {
        this.learningPlanPathRepository = learningPlanPathRepository;
    }

    /**
     * Saves a new learning plan path to the database with validation.
     *
     * @param learningPlanPath The learning plan path to be saved.
     * @return The saved learning plan path.
     * @throws InvalidLearningPlanPathDataException If the learning plan path data
     *                                              is invalid.
     * @throws DuplicateLearningPlanPathException   If a learning plan path with the
     *                                              same details already exists.
     */
    public LearningPlanPath saveLearningPlanPath(LearningPlanPath learningPlanPath) {
        // Validates data and checks for duplicates before saving
        if (learningPlanPath.getStartDate() == null || learningPlanPath.getEndDate() == null
                || learningPlanPath.getTrainer() == null || learningPlanPath.getType() == null
                || learningPlanPath.getTrainer().isEmpty() || learningPlanPath.getType().isEmpty()
                || learningPlanPath.getCourse() == null) {
            // Throws exceptions if path data is invalid or duplicate
            throw new InvalidLearningPlanPathDataException(
                    "Invalid or incomplete data provided for creating Learning Plan Path");
        }
        Long learningPlanId = learningPlanPath.getLearningPlan().getLearningPlanId();
        Course course = learningPlanPath.getCourse();
        String type = learningPlanPath.getType();

        Optional<LearningPlanPath> existingEntry = learningPlanPathRepository
                .findByLearningPlanLearningPlanIdAndCourseAndType(learningPlanId, course, type);
        if (existingEntry.isPresent()) {
            throw new DuplicateLearningPlanPathException(
                    "A learning plan path with the same course, learning plan ID, and type already exists.");
        }

        return learningPlanPathRepository.save(learningPlanPath);
    }

    /**
     * Saves a list of learning plan paths to the database with validation.
     *
     * @param learningPlanPaths The list of learning plan paths to be saved.
     * @return The list of saved learning plan paths.
     */
    public List<LearningPlanPath> saveAllLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        List<LearningPlanPath> savedPaths = new ArrayList<>();
        for (LearningPlanPath learningPlanPath : learningPlanPaths) {
            savedPaths.add(saveLearningPlanPath(learningPlanPath));
        }
        return savedPaths;
    }

    /**
     * Retrieves all learning plan paths from the database.
     *
     * @return The list of all learning plan paths.
     */
    public List<LearningPlanPath> getAllLearningPlanPaths() {
        return learningPlanPathRepository.findAll();
    }

    /**
     * Retrieves learning plan paths by learning plan ID from the database.
     *
     * @param learningPlanId The ID of the learning plan.
     * @return The list of learning plan paths with the specified learning plan ID.
     */
    public List<LearningPlanPath> getAllLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        return learningPlanPathRepository.findByLearningPlanLearningPlanId(learningPlanId);
    }

    /**
     * Retrieves learning plan paths by type from the database.
     *
     * @param type The type of the learning plan paths.
     * @return The list of learning plan paths with the specified type.
     * @throws InvalidTypeException If the type is invalid.
     */
    public List<LearningPlanPath> getAllLearningPlanPathsByType(String type) {
        if (type == null || type.isEmpty()) {
            // Throws exceptions if type is invalid or null
            throw new InvalidTypeException("Type cannot be null or empty.");
        }

        return learningPlanPathRepository.findByType(type);
    }

    /**
     * Retrieves learning plan paths by trainer from the database.
     *
     * @param trainer The trainer associated with the learning plan paths.
     * @return The list of learning plan paths with the specified trainer.
     * @throws InvalidTrainerException If the trainer is invalid.
     */
    public List<LearningPlanPath> getAllLearningPlanPathsByTrainer(String trainer) {
        // Validates trainer and retrieves paths by trainer
        if (trainer == null || trainer.isEmpty()) {
            // Throws exceptions if trainer is invalid or null
            throw new InvalidTrainerException("trainer cannot be null or empty.");
        }
        return learningPlanPathRepository.findByTrainer(trainer);
    }

    /**
     * Updates the trainer of a learning plan path by its ID in the database.
     *
     * @param pathId     The ID of the learning plan path.
     * @param newTrainer The new trainer for the learning plan path.
     * @return The updated learning plan path.
     * @throws InvalidTrainerException           If the trainer is invalid or
     *                                           incomplete.
     * @throws LearningPlanPathNotFoundException If the learning plan path with the
     *                                           specified ID is not found.
     */
    public LearningPlanPath updateLearningPlanPathTrainer(Long pathId, String newTrainer) {
        // Validates and updates the trainer of the path
        if (newTrainer == null || newTrainer.isEmpty()) {
            // Throws exceptions if trainer is invalid or null
            throw new InvalidTrainerException("Invalid or Incomplete trainer value provided");
        }
        LearningPlanPath learningPlanPath = learningPlanPathRepository.findById(pathId)
                .orElseThrow(() -> new LearningPlanPathNotFoundException(
                        NOT_FOUND_MSG + pathId));
        learningPlanPath.setTrainer(newTrainer);
        return learningPlanPathRepository.save(learningPlanPath);
    }

    /**
     * Updates the dates of a learning plan path in the database.
     *
     * @param learningPlanPathID The ID of the learning plan path.
     * @param startDate          The start date of the learning plan path.
     * @param endDate            The end date of the learning plan path.
     * @return The updated learning plan path.
     * @throws InvalidLearningPlanPathDataException If the date format is invalid or
     *                                              incomplete.
     * @throws LearningPlanPathNotFoundException    If the learning plan path with
     *                                              the specified ID is not found.
     */
    public Optional<LearningPlanPath> updateLearningPlanPathDates(Long learningPlanPathID, Date startDate,
            Date endDate) {
        if (startDate == null || endDate == null) {
            // Throws exceptions if learning plan path date format is invalid or null
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
                    NOT_FOUND_MSG + learningPlanPathID);
        }
    }

    /**
     * Deletes all learning plan paths associated with a learning plan from the
     * database.
     *
     * @param learningPlanId The ID of the learning plan.
     */
    public void deleteLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        List<LearningPlanPath> learningPlanPaths = learningPlanPathRepository
                .findByLearningPlanLearningPlanId(learningPlanId);
        for (LearningPlanPath path : learningPlanPaths) {
            deleteLearningPlanPath(path.getPathId());
        }
    }

    /**
     * Deletes a list of learning plan paths from the database.
     *
     * @param learningPlanPaths The list of learning plan paths to delete.
     */
    public void deleteLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        learningPlanPathRepository.deleteAll(learningPlanPaths);
    }

    /**
     * Deletes a learning plan path by its ID from the database.
     *
     * @param learningPlanPathId The ID of the learning plan path to delete.
     * @throws LearningPlanPathNotFoundException If the learning plan path with the
     *                                           specified ID is not found.
     */
    public void deleteLearningPlanPath(Long learningPlanPathId) {

        Optional<LearningPlanPath> learningPlanPath = learningPlanPathRepository.findById(learningPlanPathId);
        if (learningPlanPath.isPresent()) {
            learningPlanPathRepository.delete(learningPlanPath.get());
        } else {
            throw new LearningPlanPathNotFoundException(
                    NOT_FOUND_MSG + learningPlanPathId);
        }
    }
}
