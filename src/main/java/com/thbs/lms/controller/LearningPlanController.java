package com.thbs.lms.controller;

import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.service.BulkUploadService;
import com.thbs.lms.service.LearningPlanService;
import com.thbs.lms.exceptionHandler.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/learningplans")
public class LearningPlanController {

    @Autowired
    private LearningPlanService learningPlanService;

    @Autowired
    private BulkUploadService bulkUploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            bulkUploadService.uploadFile(file);
            return ResponseEntity.ok().body("File uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while uploading the file.", e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllLearningPlans() {
        try {
            List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
            return ResponseEntity.ok().body(learningPlans);
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        try {
            LearningPlan addedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);
            return ResponseEntity.ok().body(addedLearningPlan);
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/type")
    public ResponseEntity<?> findByType(@RequestParam String type) {
        try {
            List<LearningPlan> learningPlan = learningPlanService.findByType(type);
            return ResponseEntity.ok().body(learningPlan);
        } catch (LearningPlanNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/batch-id")
    public ResponseEntity<?> findByBatchID(@RequestParam Long batchID) {
        try {
            List<LearningPlan> learningPlan = learningPlanService.findByBatchID(batchID);
            return ResponseEntity.ok().body(learningPlan);
        } catch (LearningPlanNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
