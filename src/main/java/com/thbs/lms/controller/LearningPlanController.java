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
@RequestMapping("/learningplans")
public class LearningPlanController {

    @Autowired
    private LearningPlanService learningPlanService;

    @Autowired
    private BulkUploadService bulkUploadService;

    @PostMapping("/add")
    public ResponseEntity<?> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        LearningPlan addedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);
        return ResponseEntity.ok().body(addedLearningPlan);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
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

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getLearningPlansByType(@PathVariable String type) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByType(type);
        return ResponseEntity.ok().body(learningPlan);
    }

    @GetMapping("/batch/{id}")
    public ResponseEntity<?> getLearningPlansByBatchID(@PathVariable Long id) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByBatchID(id);
        return ResponseEntity.ok().body(learningPlan);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLearningPlan(@PathVariable Long id) {
        learningPlanService.deleteLearningPlan(id);
        return ResponseEntity.ok().body("LearningPlan deleted successfully.");
    }
}
