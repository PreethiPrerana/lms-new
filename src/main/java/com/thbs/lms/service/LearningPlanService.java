package com.thbs.lms.service;

import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.dto.LearningPlanDTO;
import com.thbs.lms.dto.PathDTO;
import com.thbs.lms.exception.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import com.thbs.lms.repository.LearningPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code LearningPlanService} class provides methods for managing learning
 * plans.
 */
@Service
public class LearningPlanService {

    private static final String NOT_FOUND = " not found.";
    private LearningPlanRepository learningPlanRepository;
    private LearningPlanPathService learningPlanPathService;
    private LearningPlanPathRepository learningPlanPathRepository;
    private CourseService courseService;

    /**
     * Constructs a new instance of {@code LearningPlanService} with the specified
     * dependencies.
     *
     * @param learningPlanPathService    The service for managing learning plan
     *                                   paths.
     * @param courseService              The service for managing courses.
     * @param learningPlanRepository     The repository for managing learning plans.
     * @param learningPlanPathRepository The repository for managing learning plan
     *                                   paths.
     */
    @Autowired
    public LearningPlanService(LearningPlanPathService learningPlanPathService, CourseService courseService,
            LearningPlanRepository learningPlanRepository, LearningPlanPathRepository learningPlanPathRepository) {
        this.learningPlanPathService = learningPlanPathService;
        this.learningPlanRepository = learningPlanRepository;
        this.learningPlanPathRepository = learningPlanPathRepository;
        this.courseService = courseService;
    }

    /**
     * Saves a new learning plan to the database.
     *
     * @param learningPlan The learning plan to be saved.
     * @return The saved learning plan.
     * @throws DuplicateLearningPlanException If a learning plan already exists for
     *                                        the given batch.
     * @throws InvalidLearningPlanException   If the batch ID or learning plan type
     *                                        is null.
     */
    public LearningPlan saveLearningPlan(LearningPlan learningPlan) {
        List<LearningPlan> existingLearningPlan = learningPlanRepository.findByBatchId(learningPlan.getBatchId());
        // Checks if a learning plan already exists for the given batch
        if (!existingLearningPlan.isEmpty()) {
            // Throws exception if duplicate learning plan or invalid data
            throw new DuplicateLearningPlanException(
                    "Learning plan for this batch " + learningPlan.getBatchId() + " already exists.");
        }
        if (learningPlan.getBatchId() == null || learningPlan.getType() == null || learningPlan.getType().isEmpty()) {
            throw new InvalidLearningPlanException("Batch ID or LearningPlan Type cannot be null");
        }
        return learningPlanRepository.save(learningPlan);
    }

    /**
     * Converts a learning plan to a DTO (Data Transfer Object).
     *
     * @param learningPlanId The ID of the learning plan.
     * @return The DTO representing the learning plan.
     */
    public LearningPlanDTO convertToDTO(Long learningPlanId) {
        LearningPlanDTO dto = new LearningPlanDTO();

        LearningPlan learningPlan = getLearningPlanById(learningPlanId);
        dto.setBatchId(learningPlan.getBatchId());
        dto.setLearningPlanId(learningPlanId);

        List<LearningPlanPath> relatedPaths = learningPlanPathRepository
                .findByLearningPlanLearningPlanId(learningPlanId);
        List<PathDTO> paths = new ArrayList<>();

        for (LearningPlanPath path : relatedPaths) {
            if (path.getType().equalsIgnoreCase("course")) {
                PathDTO pathDTO = new PathDTO();
                pathDTO.setLearningPlanPathId(path.getPathId());
                pathDTO.setType(path.getType());
                pathDTO.setTrainer(path.getTrainer());
                pathDTO.setStartDate(path.getStartDate());
                pathDTO.setEndDate(path.getEndDate());

                Course course = path.getCourse();
                CourseDTO courseDTO = courseService.convertToDTO(course);
                pathDTO.setCourse(courseDTO);

                paths.add(pathDTO);
            }

        }

        dto.setPath(paths);
        return dto;
    }

    /**
     * Retrieves all learning plan DTOs (Data Transfer Objects) from the database.
     *
     * @return The list of all learning plan DTOs.
     */
    public List<LearningPlanDTO> getAllLearningPlanPathDTOs() {
        List<LearningPlanDTO> dtos = new ArrayList<>();
        List<LearningPlan> learningPlans = learningPlanRepository.findAll();
        for (LearningPlan learningPlan : learningPlans) {
            LearningPlanDTO dto = convertToDTO(learningPlan.getLearningPlanId());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves all learning plan DTOs (Data Transfer Objects) filtered by batch ID
     * from the database.
     *
     * @param batchId The ID of the batch.
     * @return The list of learning plan DTOs filtered by batch ID.
     */
    public List<LearningPlanDTO> getAllLearningPlanPathDTOsByBatchId(Long batchId) {
        List<LearningPlanDTO> dtoByBatch = new ArrayList<>();
        List<LearningPlanDTO> allDTO = getAllLearningPlanPathDTOs();
        for (LearningPlanDTO DTO : allDTO) {
            if (batchId.equals(DTO.getBatchId()))
                dtoByBatch.add(DTO);
        }

        return dtoByBatch;
    }

    /**
     * Retrieves all learning plans from the database.
     *
     * @return The list of all learning plans.
     */
    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    /**
     * Retrieves a learning plan by its ID from the database.
     *
     * @param id The ID of the learning plan.
     * @return The learning plan with the specified ID.
     * @throws LearningPlanNotFoundException If the learning plan with the specified
     *                                       ID is not found.
     */
    public LearningPlan getLearningPlanById(Long id) {
        Optional<LearningPlan> optionalLearningPlan = learningPlanRepository.findById(id);
        if (optionalLearningPlan.isPresent()) {
            return optionalLearningPlan.get();
        } else {
            throw new LearningPlanNotFoundException("Learning plan with ID " + id + NOT_FOUND);
        }
    }

    /**
     * Retrieves learning plans by type from the database.
     *
     * @param type The type of the learning plans.
     * @return The list of learning plans with the specified type.
     * @throws InvalidTypeException          If the type is null.
     * @throws LearningPlanNotFoundException If no learning plans are found with the
     *                                       specified type.
     */
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
            throw new LearningPlanNotFoundException("Learning plan with Type " + type + NOT_FOUND);
        }
    }

    /**
     * Retrieves learning plans by batch ID from the database.
     *
     * @param batchID The ID of the batch.
     * @return The list of learning plans with the specified batch ID.
     * @throws InvalidBatchException         If the batch ID is null.
     * @throws LearningPlanNotFoundException If no learning plans are found for the
     *                                       specified batch ID.
     */
    public List<LearningPlan> getLearningPlansByBatchId(Long batchID) {
        // Validates batch ID and retrieves learning plans by batch ID
        if (batchID == null) {
            // Throws exception if batch ID is invalid or no learning plans found
            throw new InvalidBatchException("LearningPlan Type cannot be null");
        }
        List<LearningPlan> learningPlan = learningPlanRepository.findByBatchId(batchID);
        if (!learningPlan.isEmpty()) {
            return learningPlan;
        } else {
            throw new LearningPlanNotFoundException("Learning plan for Batch " + batchID + NOT_FOUND);
        }
    }

    /**
     * Deletes a learning plan by its ID from the database along with its associated
     * paths.
     *
     * @param id The ID of the learning plan to delete.
     * @throws LearningPlanNotFoundException If the learning plan with the specified
     *                                       ID is not found.
     */
    public void deleteLearningPlan(Long id) {
        Optional<LearningPlan> optionalLearningPlan = learningPlanRepository.findById(id);
        if (optionalLearningPlan.isPresent()) {
            // Throws exception if learning plan with given ID is not found
            learningPlanPathService.deleteLearningPlanPathsByLearningPlanId(id);
            learningPlanRepository.delete(optionalLearningPlan.get());
        } else {
            throw new LearningPlanNotFoundException("Learning plan with ID " + id + NOT_FOUND);
        }
    }


    public Long getBatchIdByLearningPlanId(Long learningPlanId) {
        LearningPlan learningPlan = learningPlanRepository.findById(learningPlanId)
                                                           .orElse(null);
        if (learningPlan != null) {
            return learningPlan.getBatchId();
        } else {
            // Handle the case where the learning plan with the given ID is not found
            return null; // Or throw an exception, depending on your requirements
        }
    }
}