package com.thbs.lms.controller;

import com.thbs.lms.exception.DuplicateTopicException;
import com.thbs.lms.exception.FileProcessingException;
import com.thbs.lms.exception.InvalidSheetFormatException;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.service.BulkUploadService;
import com.thbs.lms.service.LearningPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            System.out.println("Before upload file");
            bulkUploadService.uploadFile(file);
            System.out.println("Looks like no exception");
            return ResponseEntity.ok().body("File uploaded successfully.");
        } catch (IOException e) {
            String errorMessage = "Error processing file: " + e.getMessage();
            System.out.println("Inside catch: " + errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
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

    @PutMapping("/update-dates")
    public Optional<LearningPlan> updateLearningPlanDates(@RequestParam Long learningPlanID,
                                                          @RequestBody DateRange dateRange) {
        return learningPlanService.updateLearningPlanDates(learningPlanID, dateRange.getStartDate(), dateRange.getEndDate());
    }

    public static class DateRange {
        private Date startDate;
        private Date endDate;

        // getters and setters
        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }
}
