package com.thbs.lms.testService;

import com.thbs.lms.exceptionHandler.LearningPlanNotFoundException;
import com.thbs.lms.exceptionHandler.RepositoryOperationException;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.repository.LearningPlanRepository;
import com.thbs.lms.service.LearningPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningPlanServiceTest {

    @Mock
    private LearningPlanRepository learningPlanRepository;

    @InjectMocks
    private LearningPlanService learningPlanService;

    private LearningPlan learningPlan;

    @BeforeEach
    void setUp() {
        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanID(1L);
        learningPlan.setType("Test Type");
        learningPlan.setBatchID(1L);
    }

    @Test
    void testGetAllLearningPlans() {
        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);
        when(learningPlanRepository.findAll()).thenReturn(expectedLearningPlans);

        List<LearningPlan> actualLearningPlans = learningPlanService.getAllLearningPlans();

        assertEquals(expectedLearningPlans.size(), actualLearningPlans.size());
        assertEquals(expectedLearningPlans.get(0), actualLearningPlans.get(0));
    }

    @Test
    void testSaveLearningPlan() {
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setLearningPlanID(1L);
        learningPlan.setType("Test Learning Plan");

        when(learningPlanRepository.save(learningPlan)).thenReturn(learningPlan);
        LearningPlan savedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);
        verify(learningPlanRepository, times(1)).save(learningPlan);
        assertEquals(learningPlan, savedLearningPlan);
    }

    @Test
    void testGetLearningPlanById() {
        Long learningPlanId = 1L;
        when(learningPlanRepository.findById(learningPlanId)).thenReturn(Optional.of(learningPlan));

        LearningPlan actualLearningPlan = learningPlanService.getLearningPlanById(learningPlanId);

        assertEquals(learningPlan, actualLearningPlan);
    }

    @Test
    void testFindByType() {
        String type = "Test Type";
        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);
        when(learningPlanRepository.findByType(type)).thenReturn(expectedLearningPlans);

        List<LearningPlan> actualLearningPlans = learningPlanService.getLearningPlansByType(type);

        assertEquals(expectedLearningPlans.size(), actualLearningPlans.size());
        assertEquals(expectedLearningPlans.get(0), actualLearningPlans.get(0));
    }

    @Test
    void testFindByBatchID() {
        Long batchID = 1L;
        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);
        when(learningPlanRepository.findByBatchID(batchID)).thenReturn(expectedLearningPlans);

        List<LearningPlan> actualLearningPlans = learningPlanService.getLearningPlansByBatchID(batchID);

        assertEquals(expectedLearningPlans.size(), actualLearningPlans.size());
        assertEquals(expectedLearningPlans.get(0), actualLearningPlans.get(0));
    }

    @Test
    void testGetLearningPlanById_NotFound() {
        Long learningPlanId = 1L;
        when(learningPlanRepository.findById(learningPlanId)).thenReturn(Optional.empty());

        assertThrows(LearningPlanNotFoundException.class, () -> {
            learningPlanService.getLearningPlanById(learningPlanId);
        });
    }

    @Test
    void testFindByType_NotFound() {
        String type = "Nonexistent Type";
        when(learningPlanRepository.findByType(type)).thenReturn(new ArrayList<>());

        assertThrows(LearningPlanNotFoundException.class, () -> {
            learningPlanService.getLearningPlansByType(type);
        });
    }

    @Test
    void testFindByBatchID_NotFound() {
        Long batchID = 2L; // Assuming no learning plans exist for batchID = 2
        when(learningPlanRepository.findByBatchID(batchID)).thenReturn(new ArrayList<>());

        assertThrows(LearningPlanNotFoundException.class, () -> {
            learningPlanService.getLearningPlansByBatchID(batchID);
        });
    }

    @Test
    void testGetAllLearningPlans_RepositoryException() {
        // Stubbing the repository to throw an exception
        when(learningPlanRepository.findAll()).thenThrow(new RuntimeException("Repository exception"));

        // Verifying that a RepositoryOperationException is thrown
        assertThrows(RepositoryOperationException.class, () -> {
            learningPlanService.getAllLearningPlans();
        });
    }
}
