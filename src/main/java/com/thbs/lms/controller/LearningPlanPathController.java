package com.thbs.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.service.LearningPlanPathService;
import com.thbs.lms.utility.DateRange;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/learning-plan-paths")
public class LearningPlanPathController {

    @Autowired
    private LearningPlanPathService learningPlanPathService;

    @PostMapping("add")
    public ResponseEntity<?> createLearningPlanPath(@RequestBody LearningPlanPath learningPlanPath) {
        LearningPlanPath createdPath = learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        return ResponseEntity.ok(createdPath);
    }

    @PostMapping("addAll")
    public ResponseEntity<?> createLearningPlanPaths(@RequestBody List<LearningPlanPath> learningPlanPaths) {
        List<LearningPlanPath> createdPaths = learningPlanPathService.saveAllLearningPlanPaths(learningPlanPaths);
        return ResponseEntity.ok().body(createdPaths);
    }

    @GetMapping
    public ResponseEntity<?> getAllLearningPlanPaths() {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPaths();
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/id/{learningPlanId}")
    public ResponseEntity<?> getAllLearningPlanPathsByLearningPlanId(@PathVariable Long learningPlanId) {
        List<LearningPlanPath> paths = learningPlanPathService
                .getAllLearningPlanPathsByLearningPlanId(learningPlanId);
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getAllLearningPlanPathsByType(@PathVariable String type) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByType(type);
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/trainer/{trainer}")
    public ResponseEntity<?> getAllLearningPlanPathsByTrainer(@PathVariable String trainer) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByTrainer(trainer);
        return ResponseEntity.ok().body(paths);
    }

    @PatchMapping("/trainer/{id}")
    public ResponseEntity<?> updateTrainer(@PathVariable Long id, @RequestBody String newTrainer) {
        learningPlanPathService.updateLearningPlanPathTrainer(id, newTrainer);
        return ResponseEntity.ok().body("Trainer updated successfully");
    }

    @PatchMapping("/update-dates/{learningPlanPathID}")
    public ResponseEntity<?> updateDates(@PathVariable Long learningPlanPathID,
            @RequestBody DateRange dateRange) {
        learningPlanPathService.updateLearningPlanPathDates(learningPlanPathID, dateRange.getStartDate(),
                dateRange.getEndDate());
        return ResponseEntity.ok().body("Date Range updated successfully.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLearningPlanPath(@PathVariable Long id) {
        learningPlanPathService.deleteLearningPlanPath(id);
        return ResponseEntity.ok().body("Learning plan path deleted successfully");
    }
}
