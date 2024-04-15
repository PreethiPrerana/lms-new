package com.thbs.lms.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.dto.LearningPlanDTO;

@SpringBootTest
class LearningPlanDTOTest {

    @Test
    void testGettersAndSetters() {
        // Sample data
        Long batchId = 123L;
        Long learningPlanId = 456L;
        List<Long> learningPlanPathIds = List.of(1L, 2L, 3L);
        List<Long> courseIds = List.of(101L, 102L, 103L);
        List<List<Long>> topicIds = List.of(
                List.of(201L, 202L),
                List.of(203L, 204L),
                List.of(205L, 206L));

        // Create a LearningPlanDTO object
        LearningPlanDTO learningPlanDTO = new LearningPlanDTO();

        // Test setters
        learningPlanDTO.setBatchId(batchId);
        learningPlanDTO.setLearningPlanId(learningPlanId);
        learningPlanDTO.setLearningPlanPathIds(learningPlanPathIds);
        learningPlanDTO.setCourseIds(courseIds);
        learningPlanDTO.setTopicIds(topicIds);

        // Test getters
        assertEquals(batchId, learningPlanDTO.getBatchId());
        assertEquals(learningPlanId, learningPlanDTO.getLearningPlanId());
        assertEquals(learningPlanPathIds, learningPlanDTO.getLearningPlanPathIds());
        assertEquals(courseIds, learningPlanDTO.getCourseIds());
        assertEquals(topicIds, learningPlanDTO.getTopicIds());
    }

    @Test
    void testAllArgsConstructor() {
        // Sample data for constructor
        Long batchId = 123L;
        Long learningPlanId = 456L;
        List<Long> learningPlanPathIds = List.of(1L, 2L, 3L);
        List<Long> courseIds = List.of(101L, 102L, 103L);
        List<List<Long>> topicIds = List.of(
                List.of(201L, 202L),
                List.of(203L, 204L),
                List.of(205L, 206L));

        // Create a LearningPlanDTO object using AllArgsConstructor
        LearningPlanDTO learningPlanDTO = new LearningPlanDTO(batchId, learningPlanId, learningPlanPathIds, courseIds,
                topicIds);

        // Test getters to verify the data set by AllArgsConstructor
        assertEquals(batchId, learningPlanDTO.getBatchId());
        assertEquals(learningPlanId, learningPlanDTO.getLearningPlanId());
        assertEquals(learningPlanPathIds, learningPlanDTO.getLearningPlanPathIds());
        assertEquals(courseIds, learningPlanDTO.getCourseIds());
        assertEquals(topicIds, learningPlanDTO.getTopicIds());
    }

    @Test
    void testNoArgsConstructor() {
        // Create a LearningPlanDTO object using NoArgsConstructor
        LearningPlanDTO learningPlanDTO = new LearningPlanDTO();

        // Test getters to verify the default values set by NoArgsConstructor
        assertNull(learningPlanDTO.getBatchId());
        assertNull(learningPlanDTO.getLearningPlanId());
        assertNull(learningPlanDTO.getLearningPlanPathIds());
        assertNull(learningPlanDTO.getCourseIds());
        assertNull(learningPlanDTO.getTopicIds());
    }

}
