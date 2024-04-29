package com.thbs.lms.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.model.LearningPlan;

@SpringBootTest
class LearningPlanTest {

    @Test
    void testGettersAndSetters() {
        // Initialize a LearningPlan object
        LearningPlan learningPlan = new LearningPlan();

        // Set values using setters
        Long learningPlanId = 1L;
        String type = "Test Type";
        Long batchId = 123L;

        learningPlan.setLearningPlanId(learningPlanId);
        learningPlan.setType(type);
        learningPlan.setBatchId(batchId);

        // Test getters
        assertEquals(learningPlanId, learningPlan.getLearningPlanId());
        assertEquals(type, learningPlan.getType());
        assertEquals(batchId, learningPlan.getBatchId());
    }

    @Test
    void testNoArgsConstructor() {
        LearningPlan learningPlan = new LearningPlan();
        assertNotNull(learningPlan);
        assertNull(learningPlan.getLearningPlanId());
        assertNull(learningPlan.getType());
        assertNull(learningPlan.getBatchId());
    }

    @Test
    void testAllArgsConstructor() {
        Long learningPlanId = 1L;
        String type = "Test Type";
        Long batchId = 123L;

        LearningPlan learningPlan = new LearningPlan(learningPlanId, type, batchId);
        assertNotNull(learningPlan);
        assertEquals(learningPlanId, learningPlan.getLearningPlanId());
        assertEquals(type, learningPlan.getType());
        assertEquals(batchId, learningPlan.getBatchId());
    }
}
