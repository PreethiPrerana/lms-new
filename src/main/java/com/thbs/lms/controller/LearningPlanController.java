package com.thbs.lms.controller;

import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.service.BulkUploadService;
import com.thbs.lms.service.LearningPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/learning-plan")
@CrossOrigin("172.18.4.113:5173")
public class LearningPlanController {

    private final LearningPlanService learningPlanService;
    private final BulkUploadService bulkUploadService;

    @Autowired
    public LearningPlanController(LearningPlanService learningPlanService, BulkUploadService bulkUploadService) {
        this.learningPlanService = learningPlanService;
        this.bulkUploadService = bulkUploadService;
    }

    // Add a learningPlan
    @PostMapping
    public ResponseEntity<LearningPlan> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        LearningPlan addedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);
        return ResponseEntity.ok().body(addedLearningPlan);
    }

    // Bulk upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            bulkUploadService.uploadFile(file);
            return ResponseEntity.ok().body("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

    // Get all LearningPlans
    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return ResponseEntity.ok().body(learningPlans);
    }

    // Get all LearningPlans by type(ON-DEMAND, BOOTCAMP, ORG-WIDE, MANDATORY)
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByType(@PathVariable String type) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByType(type);
        return ResponseEntity.ok().body(learningPlan);
    }

    // Get all LearningPlans for a particular Batch
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByBatchID(@PathVariable Long batchId) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByBatchID(batchId);
        return ResponseEntity.ok().body(learningPlan);
    }

    // delete a LearningPlan by learningPlanId
    @DeleteMapping("/{learningPlanId}")
    public ResponseEntity<String> deleteLearningPlan(@PathVariable Long learningPlanId) {
        learningPlanService.deleteLearningPlan(learningPlanId);
        return ResponseEntity.ok().body("LearningPlan deleted successfully.");
    }
}
