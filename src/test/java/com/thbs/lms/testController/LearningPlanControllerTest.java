package com.thbs.lms.testController;

import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.service.LearningPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.thbs.lms.controller.LearningPlanController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningPlanControllerTest {

    @Mock
    private LearningPlanService learningPlanService;

    @InjectMocks
    private LearningPlanController learningPlanController;

    private LearningPlan learningPlan;

    @BeforeEach
    void setUp() {
        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanID(1L);
        learningPlan.setType("Test Type");
        learningPlan.setBatchID(1L);
    }

    @Test
    void testSaveLearningPlan() {
        when(learningPlanService.saveLearningPlan(learningPlan)).thenReturn(learningPlan);

        ResponseEntity<?> responseEntity = learningPlanController.saveLearningPlan(learningPlan);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlan, responseEntity.getBody());
    }

    @Test
    void testGetAllLearningPlans() {
        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);
        when(learningPlanService.getAllLearningPlans()).thenReturn(expectedLearningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getAllLearningPlans();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedLearningPlans, responseEntity.getBody());
    }

    @Test
    void testFindByType() {
        String type = "Test Type";
        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);
        when(learningPlanService.findByType(type)).thenReturn(expectedLearningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.findByType(type);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedLearningPlans, responseEntity.getBody());
    }

    @Test
    void testFindByBatchID() {
        Long batchID = 1L;
        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);
        when(learningPlanService.findByBatchID(batchID)).thenReturn(expectedLearningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.findByBatchID(batchID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedLearningPlans, responseEntity.getBody());
    }

}
