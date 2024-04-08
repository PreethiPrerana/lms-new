package com.thbs.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.exceptionHandler.DuplicateLearningPlanPathException;
import com.thbs.lms.exceptionHandler.InvalidLearningPlanPathDataException;
import com.thbs.lms.exceptionHandler.InvalidTrainerException;
import com.thbs.lms.exceptionHandler.InvalidTypeException;
import com.thbs.lms.exceptionHandler.LearningPlanPathNotFoundException;
import com.thbs.lms.exceptionHandler.RepositoryOperationException;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.service.LearningPlanPathService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/learning-plan-paths")
public class LearningPlanPathController {

    @Autowired
    private LearningPlanPathService learningPlanPathService;

    @PostMapping
    public ResponseEntity<?> createLearningPlanPath(@RequestBody LearningPlanPath learningPlanPath) {
        try {
            LearningPlanPath createdPath = learningPlanPathService.createLearningPlanPath(learningPlanPath);
            return ResponseEntity.ok(createdPath);
        } catch (DuplicateLearningPlanPathException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (InvalidLearningPlanPathDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/ID")
    public ResponseEntity<?> getAllLearningPlanPathsByLearningPlanId(@RequestBody Long learningPlanId) {
        try {
            List<LearningPlanPath> paths = learningPlanPathService
                    .getAllLearningPlanPathsByLearningPlanId(learningPlanId);
            return ResponseEntity.ok().body(paths);
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/type")
    public ResponseEntity<?> getAllLearningPlansByType(@RequestParam String type) {
        try {
            List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlansByType(type);
            return ResponseEntity.ok().body(paths);
        } catch (InvalidTypeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/trainer")
    public ResponseEntity<?> getAllLearningPlansByTrainer(@RequestParam String trainer) {
        try {
            List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlansByTrainer(trainer);
            return ResponseEntity.ok().body(paths);
        } catch (InvalidTrainerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/pathID/trainer")
    public ResponseEntity<?> updateTrainer(@RequestParam Long pathId, @RequestBody String newTrainer) {
        try {
            learningPlanPathService.updateTrainer(pathId, newTrainer);
            return ResponseEntity.ok().body("Trainer updated successfully");
        } catch (InvalidTrainerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/update-dates")
    public ResponseEntity<?> updateLearningPlanDates(@RequestParam Long learningPlanPathID,
            @RequestBody DateRange dateRange) {
        try {
            Optional<LearningPlanPath> updatedPath = learningPlanPathService.updateDates(learningPlanPathID,
                    dateRange.getStartDate(), dateRange.getEndDate());
            return ResponseEntity.ok().body("Date Range updated successfully.");
        } catch (LearningPlanPathNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("LearningPlan Path with ID: " + learningPlanPathID + " not found.");
        } catch (RepositoryOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
