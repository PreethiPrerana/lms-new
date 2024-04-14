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
@CrossOrigin("*")
public class LearningPlanController {

    private final LearningPlanService learningPlanService;
    private final BulkUploadService bulkUploadService;

    @Autowired
    public LearningPlanController(LearningPlanService learningPlanService, BulkUploadService bulkUploadService) {
        this.learningPlanService = learningPlanService;
        this.bulkUploadService = bulkUploadService;
    }

    @PostMapping
    public ResponseEntity<LearningPlan> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        LearningPlan addedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);
        return ResponseEntity.ok().body(addedLearningPlan);
    }

    // @PostMapping("/upload")
    // public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    //     try {
    //         bulkUploadService.uploadFile(file);
    //         return ResponseEntity.ok().body("File uploaded successfully.");
    //     } catch (IOException e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("Error processing file: " + e.getMessage());
    //     }
    // }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (fileName != null) {
                String[] parts = fileName.split("\\.");
                String fileExtension = parts[parts.length - 1].toUpperCase(); // Get the last part which represents the file extension
                if (fileExtension.equals("PDF") || fileExtension.equals("PNG") || fileExtension.equals("TXT") || fileExtension.equals("JPEG")) {
                    return ResponseEntity.badRequest().body("Unsupported file format: " + fileExtension);
                }
            }
            bulkUploadService.uploadFile(file);
            return ResponseEntity.ok().body("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }
    

    

    
    

    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return ResponseEntity.ok().body(learningPlans);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByType(@PathVariable String type) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByType(type);
        return ResponseEntity.ok().body(learningPlan);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByBatchID(@PathVariable Long batchId) {
        List<LearningPlan> learningPlan = learningPlanService.getLearningPlansByBatchID(batchId);
        return ResponseEntity.ok().body(learningPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLearningPlan(@PathVariable Long id) {
        learningPlanService.deleteLearningPlan(id);
        return ResponseEntity.ok().body("LearningPlan deleted successfully.");
    }
}
