package com.thbs.lms.controller;

import com.thbs.lms.dto.LearningPlanDTO;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.service.BulkUploadService;
import com.thbs.lms.service.LearningPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The {@code LearningPlanController} class handles HTTP requests related to
 * learning plans.
 * It provides endpoints for adding, retrieving, and deleting learning plans, as
 * well as bulk upload functionality.
 */
@RestController
@RequestMapping("/learning-plan")
@CrossOrigin("172.18.4.113:5173, 172.18.4.195:5173 ")
public class LearningPlanController {

    /**
     * The service responsible for handling business logic related to learning
     * plans.
     */
    private final LearningPlanService learningPlanService;

    /**
     * The service responsible for handling bulk upload functionality.
     */
    private final BulkUploadService bulkUploadService;

    /**
     * Constructs a new {@code LearningPlanController} with the specified services.
     *
     * @param learningPlanService the learning plan service
     * @param bulkUploadService   the bulk upload service
     */
    @Autowired
    public LearningPlanController(LearningPlanService learningPlanService, BulkUploadService bulkUploadService) {
        this.learningPlanService = learningPlanService;
        this.bulkUploadService = bulkUploadService;
    }

    /**
     * Adds a learning plan.
     *
     * @param learningPlan the learning plan to add
     * @return a response entity containing the added learning plan
     */
    @PostMapping
    public ResponseEntity<LearningPlan> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        LearningPlan addedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);
        return ResponseEntity.ok().body(addedLearningPlan);
    }

    /**
     * Handles bulk upload functionality.
     *
     * @param file the file to upload
     * @return a response entity indicating the success of the upload operation
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        bulkUploadService.uploadFile(file);
        return ResponseEntity.ok().body("File uploaded successfully.");
    }

    /**
     * Retrieves all learning plans.
     *
     * @return a response entity containing a list of all learning plans
     */
    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return ResponseEntity.ok().body(learningPlans);
    }

    /**
     * Retrieves learning plan DTOs.
     *
     * @return a response entity containing a list of learning plan DTOs
     */
    @GetMapping("/dto")
    public ResponseEntity<List<LearningPlanDTO>> getAllLearningPlanPathDTOs() {
        List<LearningPlanDTO> dto = learningPlanService.getAllLearningPlanPathDTOs();
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Retrieves learning plan DTOs by batch ID.
     *
     * @param batchId the batch ID
     * @return a response entity containing a list of learning plan DTOs for the
     *         specified batch ID
     */
    @GetMapping("/dto/{batchId}")
    public ResponseEntity<List<LearningPlanDTO>> getAllLearningPlanPathDTOsByBatchId(@PathVariable Long batchId) {
        List<LearningPlanDTO> dtos = learningPlanService.getAllLearningPlanPathDTOsByBatchId(batchId);
        return ResponseEntity.ok().body(dtos);
    }

    /**
     * Retrieves all learning plans by type.
     *
     * @param type the type of learning plans to retrieve
     * @return a response entity containing a list of learning plans for the
     *         specified type
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByType(@PathVariable String type) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByType(type);
        return ResponseEntity.ok().body(learningPlan);
    }

    /**
     * Retrieves all learning plans for a particular batch.
     *
     * @param batchId the batch ID
     * @return a response entity containing a list of learning plans for the
     *         specified batch
     */
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByBatchID(@PathVariable Long batchId) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByBatchId(batchId);
        return ResponseEntity.ok().body(learningPlan);
    }

    /**
     * Deletes a learning plan by its ID.
     *
     * @param learningPlanId the ID of the learning plan to delete
     * @return a response entity indicating the success of the deletion operation
     */
    @DeleteMapping("/{learningPlanId}")
    public ResponseEntity<String> deleteLearningPlan(@PathVariable Long learningPlanId) {
        learningPlanService.deleteLearningPlan(learningPlanId);
        return ResponseEntity.ok().body("LearningPlan deleted successfully.");
    }
}
