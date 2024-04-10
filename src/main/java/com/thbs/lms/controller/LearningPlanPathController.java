package com.thbs.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.model.LearningPlan;
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

    @PostMapping("addAll")
    public ResponseEntity<?> createLearningPlanPaths(@RequestBody List<LearningPlanPath> learningPlanPaths) {
        List<LearningPlanPath> createdPaths = learningPlanPathService.saveAllLearningPlanPaths(learningPlanPaths);
        return ResponseEntity.ok().body(createdPaths);
    }

    @GetMapping("/ID")
    public ResponseEntity<?> getAllLearningPlanPathsByLearningPlanId(@RequestBody Long learningPlanId) {
        List<LearningPlanPath> paths = learningPlanPathService
                .getAllLearningPlanPathsByLearningPlanId(learningPlanId);
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getAllLearningPlansByType(@PathVariable String type) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByType(type);
        return ResponseEntity.ok().body(paths);
    }

    @GetMapping("/trainer/{trainer}")
    public ResponseEntity<?> getAllLearningPlansByTrainer(@PathVariable String trainer) {
        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByTrainer(trainer);
        return ResponseEntity.ok().body(paths);
    }

    @PatchMapping("/trainer/{id}")
public ResponseEntity<?> updateTrainer(@PathVariable Long id, @RequestBody String newTrainer) {
    
    LearningPlanPath updatedTrainer=learningPlanPathService.updateLearningPlanPathTrainer(id, newTrainer);
    if (updatedTrainer!=null) {
        return ResponseEntity.ok().body("Trainer updated successfully");
    } else {
        return ResponseEntity.notFound().build();
    }
}


@PatchMapping("/update-dates/{id}")
public ResponseEntity<?> updateLearningPlanDates(@PathVariable Long learningPlanPathID,
        @RequestBody DateRange dateRange) {
   
    Optional<LearningPlanPath> updateLearningPlanDates=learningPlanPathService.updateLearningPlanPathDates(learningPlanPathID, dateRange.getStartDate(),
            dateRange.getEndDate());
    if (updateLearningPlanDates!=null) {
        return ResponseEntity.ok().body("Date Range updated successfully.");
    } else {
        return ResponseEntity.notFound().build(); // Or return any appropriate error response
    }
}

   

    public static class DateRange {
        private Date startDate;
        private Date endDate;

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
