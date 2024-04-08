package com.thbs.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.service.LearningPlanPathService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/learning-plan-paths")
public class LearningPlanPathController {

    @Autowired
    private LearningPlanPathService learningPlanPathService;

    @PostMapping
    public ResponseEntity<?> createLearningPlanPath(@RequestBody LearningPlanPath learningPlanPath) {
        LearningPlanPath createdPath = learningPlanPathService.createLearningPlanPath(learningPlanPath);
        return ResponseEntity.ok(createdPath);
    }

    @GetMapping("/ID")
    public ResponseEntity<?> getAllLearningPlanPathsByLearningPlanId(@RequestBody Long learningPlanId) {
        List<LearningPlanPath> paths = learningPlanPathService
                .getAllLearningPlanPathsByLearningPlanId(learningPlanId);
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/type")
    public ResponseEntity<?> getAllLearningPlansByType(@RequestParam String type) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlansByType(type);
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/trainer")
    public ResponseEntity<?> getAllLearningPlansByTrainer(@RequestParam String trainer) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlansByTrainer(trainer);
        return ResponseEntity.ok().body(paths);
    }

    @PatchMapping("/pathID/trainer")
    public ResponseEntity<?> updateTrainer(@RequestParam Long pathId, @RequestBody String newTrainer) {
        learningPlanPathService.updateTrainer(pathId, newTrainer);
        return ResponseEntity.ok().body("Trainer updated successfully");
    }

    @PatchMapping("/update-dates")
    public ResponseEntity<?> updateLearningPlanDates(@RequestParam Long learningPlanPathID,
            @RequestBody DateRange dateRange) {
        Optional<LearningPlanPath> updatedPath = learningPlanPathService.updateDates(learningPlanPathID,
                dateRange.getStartDate(), dateRange.getEndDate());
        return ResponseEntity.ok().body("Date Range updated successfully.");
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
