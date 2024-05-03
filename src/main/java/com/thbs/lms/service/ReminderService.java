package com.thbs.lms.service;

import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.dto.ReminderDTO;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReminderService {

    @Autowired
    private LearningPlanPathRepository learningPlanPathRepository;

    @Autowired
    private LearningPlanService learningPlanService;

    @Autowired
    private CourseService courseService;

    public List<ReminderDTO> generateReminders() {
        List<ReminderDTO> reminders = new ArrayList<>();

        // Get today's date
        LocalDate today = LocalDate.now();

        // Fetch MCQ reminders
        List<LearningPlanPath> mcqPaths = learningPlanPathRepository.findByType("MCQ");
        for (LearningPlanPath mcqPath : mcqPaths) {
            Long batchId = learningPlanService.getBatchIdByLearningPlanId(mcqPath.getLearningPlan().getBatchId());
            CourseDTO courseDTO = courseService.convertToDTO(courseService.getCourseById(mcqPath.getCourse().getCourseId()));

            List<CourseDTO> courseList = new ArrayList<>();
            courseList.add(courseDTO);

            ReminderDTO reminderDTO = new ReminderDTO();
            reminderDTO.setType(mcqPath.getType());
            reminderDTO.setStartDate(mcqPath.getStartDate());
            reminderDTO.setEndDate(mcqPath.getEndDate());
            reminderDTO.setBatchId(batchId);
            reminderDTO.setCourseDTO(courseList);

            if (!mcqPath.getEndDate().isBefore(today) && !mcqPath.getStartDate().isAfter(today)) {
                reminders.add(reminderDTO);
            }
        }

        // Fetch evaluation reminders
        List<LearningPlanPath> evaluationPaths = learningPlanPathRepository.findByType("Evaluation");
        Map<String, ReminderDTO> evaluationReminderMap = new HashMap<>();
        for (LearningPlanPath evaluationPath : evaluationPaths) {
            String key = evaluationPath.getStartDate().toString() + "_" + evaluationPath.getEndDate().toString();
            ReminderDTO reminderDTO = evaluationReminderMap.get(key);
            if (reminderDTO == null) {
                reminderDTO = new ReminderDTO();
                reminderDTO.setType("Evaluation");
                reminderDTO.setStartDate(evaluationPath.getStartDate());
                reminderDTO.setEndDate(evaluationPath.getEndDate());
                reminderDTO.setCourseDTO(new ArrayList<>());

                Long batchId = learningPlanService.getBatchIdByLearningPlanId(evaluationPath.getLearningPlan().getBatchId());
                reminderDTO.setBatchId(batchId);

                evaluationReminderMap.put(key, reminderDTO);
            }
            if (!evaluationPath.getEndDate().isBefore(today) && !evaluationPath.getStartDate().isAfter(today) &&
                    !reminderDTO.getCourseDTO().stream().anyMatch(courseDTO -> 
                        evaluationPath.getCourse().getCourseId().equals(courseDTO.getCourseId()))) {
                CourseDTO courseDTO = courseService.convertToDTO(courseService.getCourseById(evaluationPath.getCourse().getCourseId()));
                reminderDTO.getCourseDTO().add(courseDTO);
            }
        }
        reminders.addAll(evaluationReminderMap.values());

        return reminders;
    }

}
