package com.thbs.lms.service;

import com.thbs.lms.dto.LearningPlanDTO;
import com.thbs.lms.exception.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.LearningPlanPathRepository;
import com.thbs.lms.repository.LearningPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanService {

    private LearningPlanRepository learningPlanRepository;
    private LearningPlanPathService learningPlanPathService;
    private LearningPlanPathRepository learningPlanPathRepository;
    private TopicService topicService;

    @Autowired
    public LearningPlanService(LearningPlanPathService learningPlanPathService, TopicService topicService,
            LearningPlanRepository learningPlanRepository, LearningPlanPathRepository learningPlanPathRepository) {
        this.learningPlanPathService = learningPlanPathService;
        this.learningPlanRepository = learningPlanRepository;
        this.learningPlanPathRepository = learningPlanPathRepository;
        this.topicService = topicService;
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

    // Converts a LearningPlanPath entity to a LearningPlanPathDTO
    public LearningPlanDTO convertToDTO(Long learningPlanId) {
        LearningPlanDTO dto = new LearningPlanDTO();

        LearningPlan learningPlan = getLearningPlanById(learningPlanId);
        dto.setBatchId(learningPlan.getBatchID());
        dto.setLearningPlanId(learningPlanId);

        List<LearningPlanPath> relatedPaths = learningPlanPathRepository
                .findByLearningPlanLearningPlanID(learningPlanId);
        List<Long> learningPlanPathIds = new ArrayList<>();
        List<Long> courseIds = new ArrayList<>();
        List<List<Long>> topicIdsList = new ArrayList<>();

        for (LearningPlanPath path : relatedPaths) {
            if (path.getType().equalsIgnoreCase("Course")) {
                learningPlanPathIds.add(path.getPathID());
                Course course = path.getCourse();
                courseIds.add(course.getCourseID());
                List<Long> topicIds = new ArrayList<>();
                List<Topic> topics = topicService.getTopicsByCourse(course);
                for (Topic topic : topics) {
                    topicIds.add(topic.getTopicID());
                }
                topicIdsList.add(topicIds);
            }

        }

        dto.setLearningPlanPathIds(learningPlanPathIds);
        dto.setCourseIds(courseIds);
        dto.setTopicIds(topicIdsList);

        return dto;
    }

    // Retrieves all learning plan paths as DTOs
    public List<LearningPlanDTO> getAllLearningPlanPathDTOs() {
        List<LearningPlanDTO> dtos = new ArrayList<>();
        List<LearningPlan> learningPlans = learningPlanRepository.findAll();
        for (LearningPlan learningPlan : learningPlans) {
            LearningPlanDTO dto = convertToDTO(learningPlan.getLearningPlanID());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<LearningPlanDTO> getAllLearningPlanPathDTOsByBatchId(Long batchId) {
        List<LearningPlanDTO> dtoByBatch = new ArrayList<>();
        List<LearningPlanDTO> allDTO = getAllLearningPlanPathDTOs();
        for (LearningPlanDTO DTO : allDTO) {
            if (batchId.equals(DTO.getBatchId()))
                dtoByBatch.add(DTO);
        }

        return dtoByBatch;
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
