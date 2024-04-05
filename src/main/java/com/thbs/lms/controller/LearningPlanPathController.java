package com.thbs.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.service.LearningPlanPathService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/learning-plan-paths")
public class LearningPlanPathController {

    @Autowired
    private LearningPlanPathService learningPlanPathService;

    @PostMapping
    public LearningPlanPath createLearningPlanPath(@RequestBody LearningPlanPath learningPlanPath) {
        return learningPlanPathService.createLearningPlanPath(learningPlanPath);
    }

    @GetMapping("/ID")
    public List<LearningPlanPath> getAllLearningPlanPathsByLearningPlanId(@RequestBody Long learningPlanId) {
        return learningPlanPathService.getAllLearningPlanPathsByLearningPlanId(learningPlanId);
    }

    @GetMapping("/type")
    public List<LearningPlanPath> getAllLearningPlansByType(@RequestParam String type) {
        return learningPlanPathService.getAllLearningPlansByType(type);
    }

    @GetMapping("/trainer")
    public List<LearningPlanPath> getAllLearningPlansByTrainer(@RequestParam String trainer) {
        return learningPlanPathService.getAllLearningPlansByTrainer(trainer);

    }

    @PatchMapping("/pathID/trainer")
    public String updateTrainer(@RequestParam Long pathId, @RequestBody String newTrainer) {
        learningPlanPathService.updateTrainer(pathId, newTrainer);
        return "Trainer updated successfully";
    }

    // @PutMapping("/pathID/dates")
    // public String updateDates(@RequestParam Long pathId,
    //         @RequestBody Date newStartDate,
    //         @RequestBody Date newEndDate) {
    //     learningPlanPathService.updateDates(pathId, newStartDate, newEndDate);
    //     return "Dates updated successfully";
    // }
    @PatchMapping("/update-dates")
    public Optional<LearningPlanPath> updateLearningPlanDates(@RequestParam Long learningPlanPathID,
                                                          @RequestBody DateRange dateRange) {
        return learningPlanPathService.updateDates(learningPlanPathID, dateRange.getStartDate(), dateRange.getEndDate());
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
