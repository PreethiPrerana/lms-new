package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// import com.thbs.lms.config.MailConfig;
import com.thbs.lms.dto.EmployeeDTO;
import com.thbs.lms.dto.ReminderDTO;
import jakarta.mail.MessagingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmailReminderLogic {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailService emailService;

    // This method will be invoked daily at 9:30 AM
    @Scheduled(cron = "0 30 9 * * ?")
    public void sendReminderEmails() {
        // Get assessments and evaluations from the database
        List<ReminderDTO> assessmentsAndEvaluations = getAssessmentsAndEvaluations();
        System.out.println("assessmentsAndEvaluations: "+ assessmentsAndEvaluations);

        for (ReminderDTO reminderDTO : assessmentsAndEvaluations) {
            Date assessmentDate = reminderDTO.getStartDate();
            System.out.println("assessmentDate: "+ assessmentDate);
            // Calculate 3 days prior to the assessment date
            Calendar reminderCalendar = Calendar.getInstance();
            reminderCalendar.setTime(assessmentDate);
            reminderCalendar.add(Calendar.DATE, -3);
            Date reminderDate = reminderCalendar.getTime();
            System.out.println("reminderDate: "+ reminderDate);

            if (assessmentDate != null && reminderDate.before(assessmentDate)) {
                System.out.println("Inside if block");
                // Extract employee details for the batch
                Long batchId = reminderDTO.getBatchId();
                String endpointUrl = "http://batch-management-service/batch/employees/" + batchId;
                EmployeeDTO[] employees = restTemplate.getForObject(endpointUrl, EmployeeDTO[].class);
                System.out.println("employees: "+ employees);

                // Prepare and send emails to employees
                for (EmployeeDTO employee : employees) {
                    System.out.println("Inside for loop");
                    String[] to = {employee.getEmail()};
                    String subject = "Reminder: Upcoming " + reminderDTO.getType() + " - " + reminderDTO.getCourseDTO()/*.getCourseName()*/;
                    String text = "Dear " + employee.getName() + ",\n\nThis is a reminder that " + reminderDTO.getType() + " for the course "
                            + reminderDTO.getCourseDTO()/*.getCourseName()*/ + " is scheduled to start on " + reminderDTO.getStartDate() + ".\n\nRegards,\nYour Company";
                    System.out.println("courseName: "+ reminderDTO.getCourseDTO()/*.getCourseName()*/);        
                    try {
                        emailService.sendEmail(to, subject, text);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        // Handle email sending exception
                        System.out.println("Email Error!");
                    }
                }
            }    
        }
    }

    private List<ReminderDTO> getAssessmentsAndEvaluations() {
        throw new UnsupportedOperationException("Unimplemented method 'getAssessmentsAndEvaluations'");
    }
}
