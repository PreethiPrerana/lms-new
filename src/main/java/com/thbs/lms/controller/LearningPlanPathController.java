package com.thbs.lms.controller;

import org.springframework.web.bind.annotation.*;

import com.thbs.lms.dto.ReminderDTO;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.service.LearningPlanPathService;
import com.thbs.lms.service.ReminderService;
import com.thbs.lms.utility.DateRange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * The {@code LearningPlanPathController} class handles HTTP requests related to
 * learning plan paths.
 * It provides endpoints for adding, retrieving, updating, and deleting learning
 * plan paths.
 */
@RestController
@RequestMapping("/learning-plan-path")
@CrossOrigin("172.18.4.113:5173, 172.18.4.195:5173")
public class LearningPlanPathController {

    /**
     * The service responsible for handling business logic related to learning plan
     * paths.
     */
    private final LearningPlanPathService learningPlanPathService;
    private final ReminderService reminderService;

    /**
     * Constructs a new {@code LearningPlanPathController} with the specified
     * service.
     *
     * @param learningPlanPathService the learning plan path service
     */
    @Autowired
    public LearningPlanPathController(LearningPlanPathService learningPlanPathService,
            ReminderService reminderService) {
        this.learningPlanPathService = learningPlanPathService;
        this.reminderService = reminderService;
    }

    /**
     * Adds a learning plan path.
     *
     * @param learningPlanPath the learning plan path to add
     * @return a response entity containing the added learning plan path
     */
    @PostMapping
    public ResponseEntity<LearningPlanPath> createLearningPlanPath(@RequestBody LearningPlanPath learningPlanPath) {
        LearningPlanPath createdPath = learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        return ResponseEntity.ok(createdPath);
    }

    /**
     * Adds multiple learning plan paths.
     *
     * @param learningPlanPaths the learning plan paths to add
     * @return a response entity containing a list of added learning plan paths
     */
    @PostMapping("/multiple")
    public ResponseEntity<List<LearningPlanPath>> createLearningPlanPaths(
            @RequestBody List<LearningPlanPath> learningPlanPaths) {
        List<LearningPlanPath> createdPaths = learningPlanPathService.saveAllLearningPlanPaths(learningPlanPaths);
        return ResponseEntity.ok().body(createdPaths);
    }

    /**
     * Retrieves all learning plan paths.
     *
     * @return a response entity containing a list of all learning plan paths
     */
    @GetMapping
    public ResponseEntity<List<LearningPlanPath>> getAllLearningPlanPaths() {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPaths();
        return ResponseEntity.ok().body(paths);
    }

    /**
     * Retrieves all learning plan paths associated with a particular learning plan
     * ID.
     *
     * @param learningPlanId the learning plan ID
     * @return a response entity containing a list of learning plan paths for the
     *         specified learning plan ID
     */
    @GetMapping("/learning-plan-id/{learningPlanId}")
    public ResponseEntity<List<LearningPlanPath>> getAllLearningPlanPathsByLearningPlanId(
            @PathVariable Long learningPlanId) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByLearningPlanId(learningPlanId);
        return ResponseEntity.ok().body(paths);
    }

    /**
     * Retrieves all learning plan paths by type.
     *
     * @param type the type of learning plan paths to retrieve
     * @return a response entity containing a list of learning plan paths for the
     *         specified type
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LearningPlanPath>> getAllLearningPlanPathsByType(@PathVariable String type) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByType(type);
        return ResponseEntity.ok().body(paths);
    }

    /**
     * Retrieves all learning plan paths associated with a particular trainer.
     *
     * @param trainerName the trainer name
     * @return a response entity containing a list of learning plan paths for the
     *         specified trainer
     */
    @GetMapping("/trainer/{trainerName}")
    public ResponseEntity<List<LearningPlanPath>> getAllLearningPlanPathsByTrainer(@PathVariable String trainerName) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByTrainer(trainerName);
        return ResponseEntity.ok().body(paths);
    }

    /**
     * Updates the trainer for a particular learning plan path.
     *
     * @param learningPlanPathId the learning plan path ID
     * @param newTrainer         the new trainer
     * @return a response entity indicating the success of the update operation
     */
    @PatchMapping("/trainer/{learningPlanPathId}")
    public ResponseEntity<String> updateTrainer(@PathVariable Long learningPlanPathId, @RequestBody String newTrainer) {
        learningPlanPathService.updateLearningPlanPathTrainer(learningPlanPathId, newTrainer);
        return ResponseEntity.ok().body("Trainer updated successfully");
    }

    /**
     * Updates the start date and end date for a particular learning plan path.
     *
     * @param learningPlanPathId the learning plan path ID
     * @param dateRange          the date range containing the start and end dates
     * @return a response entity indicating the success of the update operation
     */
    @PatchMapping("/update-dates/{learningPlanPathId}")
    public ResponseEntity<String> updateDates(@PathVariable Long learningPlanPathId,
            @RequestBody DateRange dateRange) {
        learningPlanPathService.updateLearningPlanPathDates(learningPlanPathId, dateRange.getStartDate(),
                dateRange.getEndDate());
        return ResponseEntity.ok().body("Date Range updated successfully.");
    }

    /**
     * Deletes a learning plan path by its ID.
     *
     * @param learningPlanPathId the ID of the learning plan path to delete
     * @return a response entity indicating the success of the deletion operation
     */
    @DeleteMapping("/{learningPlanPathId}")
    public ResponseEntity<String> deleteLearningPlanPath(@PathVariable Long learningPlanPathId) {
        learningPlanPathService.deleteLearningPlanPath(learningPlanPathId);
        return ResponseEntity.ok().body("Learning plan path deleted successfully");
    }

}