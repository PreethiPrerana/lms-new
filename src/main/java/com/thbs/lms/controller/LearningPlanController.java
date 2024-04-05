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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            bulkUploadService.uploadFile(file);
            return ResponseEntity.ok().body("File uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading the file.");
        }
    }

    @GetMapping
    public List<LearningPlan> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return learningPlans;
    }

    @PostMapping("/save")
    public LearningPlan saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        return learningPlanService.saveLearningPlan(learningPlan);
    }

    @GetMapping("/type")
    public List<LearningPlan> findByType(@RequestParam String type) {
        return learningPlanService.findByType(type);
    }

    @GetMapping("/batch-id")
    public List<LearningPlan> findByBatchID(@RequestParam Long batchID) {
        return learningPlanService.findByBatchID(batchID);
    }

}
