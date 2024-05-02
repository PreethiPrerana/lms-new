package com.thbs.lms.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReminderDTO {
    private Long batchId;
    private String type;
    private Date startDate;
    private Date endDate;
    private List<CourseDTO> courseDTO;
}
