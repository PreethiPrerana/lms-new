package com.thbs.lms.controller;

import com.thbs.lms.exceptionHandler.DuplicateLearningPlanException;
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
@RequestMapping("/learningplans")
public class LearningPlanController {

    @Autowired
    private LearningPlanService learningPlanService;

    @Autowired
    private BulkUploadService bulkUploadService;

    @PostMapping
    public ResponseEntity<?> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        try {
            LearningPlan addedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);
            return ResponseEntity.ok().body(addedLearningPlan);
        } catch (DuplicateLearningPlanException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Failed to save learning plan: " + e.getMessage());
        }
        }

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

    @GetMapping
    public ResponseEntity<?> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return ResponseEntity.ok().body(learningPlans);
    }

    @GetMapping("/type")
    public ResponseEntity<?> findByType(@RequestParam String type) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByType(type);
        return ResponseEntity.ok().body(learningPlan);
    }

    @GetMapping("/batch-id")
    public ResponseEntity<?> findByBatchID(@RequestParam Long batchID) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByBatchID(batchID);
        return ResponseEntity.ok().body(learningPlan);
    }
}
