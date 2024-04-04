package com.thbs.lms.controller;

import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.service.LearningPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/learningplans")
public class LearningPlanController {

    @Autowired
    private LearningPlanService learningPlanService;

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
