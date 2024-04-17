package com.thbs.lms.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.thbs.lms.dto.PathDTO;

class PathDTOTest {

    @Test
    void testGettersAndSetters() {
        // Sample data
        Long learningPlanPathId = 123L;
        String type = "type1";

        // Create a PathDTO object
        PathDTO pathDTO = new PathDTO();

        // Test setters
        pathDTO.setLearningPlanPathId(learningPlanPathId);
        pathDTO.setType(type);

        // Test getters
        assertEquals(learningPlanPathId, pathDTO.getLearningPlanPathId());
        assertEquals(type, pathDTO.getType());
    }

    @Test
    void testAllArgsConstructor() {
        // Sample data for constructor
        Long learningPlanPathId = 123L;
        String type = "type1";

        // Create a PathDTO object using AllArgsConstructor
        PathDTO pathDTO = new PathDTO(learningPlanPathId, type);

        // Test getters to verify the data set by AllArgsConstructor
        assertEquals(learningPlanPathId, pathDTO.getLearningPlanPathId());
        assertEquals(type, pathDTO.getType());
    }

    @Test
    void testNoArgsConstructor() {
        // Create a PathDTO object using NoArgsConstructor
        PathDTO pathDTO = new PathDTO();

        // Test getters to verify the default values set by NoArgsConstructor
        assertNull(pathDTO.getLearningPlanPathId());
        assertNull(pathDTO.getType());
    }
}

