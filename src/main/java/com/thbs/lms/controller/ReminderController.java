package com.thbs.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thbs.lms.dto.ReminderDTO;
import com.thbs.lms.service.ReminderService;

@RestController
@RequestMapping("/reminder")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping
    public List<ReminderDTO> generateReminders() {
        return reminderService.generateReminders();
    }
}
