package com.thbs.lms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearningPlanDTO {
    private Long batchId;
    private Long learningPlanId;
    private List<Long> learningPlanPathIds;
    private List<Long> courseIds;
    private List<List<Long>> topicIds;

}
