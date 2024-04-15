package com.thbs.lms.controller;

import org.springframework.web.bind.annotation.*;

import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.service.LearningPlanPathService;
import com.thbs.lms.utility.DateRange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/learning-plan-path")
@CrossOrigin("*")
public class LearningPlanPathController {

    private final LearningPlanPathService learningPlanPathService;

    @Autowired
    public LearningPlanPathController(LearningPlanPathService learningPlanPathService) {
        this.learningPlanPathService = learningPlanPathService;
    }

    @PostMapping
    public ResponseEntity<LearningPlanPath> createLearningPlanPath(@RequestBody LearningPlanPath learningPlanPath) {
        LearningPlanPath createdPath = learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        return ResponseEntity.ok(createdPath);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<LearningPlanPath>> createLearningPlanPaths(
            @RequestBody List<LearningPlanPath> learningPlanPaths) {
        List<LearningPlanPath> createdPaths = learningPlanPathService.saveAllLearningPlanPaths(learningPlanPaths);
        return ResponseEntity.ok().body(createdPaths);
    }

    @GetMapping
    public ResponseEntity<List<LearningPlanPath>> getAllLearningPlanPaths() {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPaths();
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/learning-plan-id/{learningPlanId}")
    public ResponseEntity<List<LearningPlanPath>> getAllLearningPlanPathsByLearningPlanId(
            @PathVariable Long learningPlanId) {
        List<LearningPlanPath> paths = learningPlanPathService
                .getAllLearningPlanPathsByLearningPlanId(learningPlanId);
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<LearningPlanPath>> getAllLearningPlanPathsByType(@PathVariable String type) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByType(type);
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/trainer/{trainerName}")
    public ResponseEntity<List<LearningPlanPath>> getAllLearningPlanPathsByTrainer(@PathVariable String trainerName) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByTrainer(trainerName);
        return ResponseEntity.ok().body(paths);
    }

    @PatchMapping("/trainer/{learningPlanPathId}")
    public ResponseEntity<String> updateTrainer(@PathVariable Long learningPlanPathId, @RequestBody String newTrainer) {
        learningPlanPathService.updateLearningPlanPathTrainer(learningPlanPathId, newTrainer);
        return ResponseEntity.ok().body("Trainer updated successfully");
    }

    @PatchMapping("/update-dates/{learningPlanPathId}")
    public ResponseEntity<String> updateDates(@PathVariable Long learningPlanPathId,
            @RequestBody DateRange dateRange) {
        learningPlanPathService.updateLearningPlanPathDates(learningPlanPathId, dateRange.getStartDate(),
                dateRange.getEndDate());
        return ResponseEntity.ok().body("Date Range updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLearningPlanPath(@PathVariable Long id) {
        learningPlanPathService.deleteLearningPlanPath(id);
        return ResponseEntity.ok().body("Learning plan path deleted successfully");
    }
}
