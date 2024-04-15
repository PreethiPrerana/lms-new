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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private LearningPlanDTO convertToDTO(Long learningPlanId) {
        LearningPlanDTO dto = new LearningPlanDTO();

        dto.setBatchId(getLearningPlanById(learningPlanId).getBatchID());

        // Fetch relevant LearningPlanPaths based on the provided LearningPlanID
        List<LearningPlanPath> relatedPaths = learningPlanPathRepository
                .findByLearningPlanLearningPlanID(learningPlanId);

        dto.setLearningPlanId(learningPlanId);

        List<Long> learningPlanPathIds = relatedPaths.stream()
                .map(LearningPlanPath::getPathID)
                .collect(Collectors.toList());
        dto.setLearningPlanPathIds(learningPlanPathIds);

        List<Course> relatedCourses = relatedPaths.stream()
                .map(LearningPlanPath::getCourse)
                .collect(Collectors.toList());
        List<Long> courseIds = relatedCourses.stream()
                .map(Course::getCourseID)
                .collect(Collectors.toList());
        dto.setCourseIds(courseIds);

        List<List<Long>> topicIdsList = relatedCourses.stream()
                .map(course -> topicService.getTopicsByCourse(course))
                .map(topics -> topics.stream().map(Topic::getTopicID)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        dto.setTopicIds(topicIdsList);

        return dto;
    }

    // Retrieves all learning plan paths as DTOs
    public List<LearningPlanDTO> getAllLearningPlanPathDTOs() {
        List<LearningPlan> learningPlans = learningPlanRepository.findAll();
        return learningPlans.stream()
                .map(LearningPlan::getLearningPlanID)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
