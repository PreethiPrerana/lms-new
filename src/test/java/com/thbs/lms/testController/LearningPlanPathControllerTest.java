package com.thbs.lms.testController;

import com.thbs.lms.controller.LearningPlanPathController;
import com.thbs.lms.controller.LearningPlanPathController.DateRange;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.service.LearningPlanPathService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningPlanPathControllerTest {

    @Mock
    private LearningPlanPathService learningPlanPathService;

    @InjectMocks
    private LearningPlanPathController learningPlanPathController;

    @Test
    void testCreateLearningPlanPath() {
        // Given
        LearningPlanPath learningPlanPath = new LearningPlanPath();

        when(learningPlanPathService.createLearningPlanPath(any())).thenReturn(learningPlanPath);

        // When
        ResponseEntity<?> responseEntity = learningPlanPathController.createLearningPlanPath(learningPlanPath);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPath, responseEntity.getBody());
        verify(learningPlanPathService, times(1)).createLearningPlanPath(any());
    }

    @Test
    void testGetAllLearningPlanPathsByLearningPlanId() {
        // Given
        Long learningPlanId = 1L;
        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        when(learningPlanPathService.getAllLearningPlanPathsByLearningPlanId(learningPlanId))
                .thenReturn(learningPlanPaths);

        // When
        ResponseEntity<?> responseEntity = learningPlanPathController
                .getAllLearningPlanPathsByLearningPlanId(learningPlanId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPaths, responseEntity.getBody());
        verify(learningPlanPathService, times(1)).getAllLearningPlanPathsByLearningPlanId(learningPlanId);
    }

    @Test
    void testGetAllLearningPlansByType() {
        // Given
        String type = "Test Type";
        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        when(learningPlanPathService.getAllLearningPlansByType(type)).thenReturn(learningPlanPaths);

        // When
        ResponseEntity<?> responseEntity = learningPlanPathController.getAllLearningPlansByType(type);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPaths, responseEntity.getBody());
        verify(learningPlanPathService, times(1)).getAllLearningPlansByType(type);
    }

    @Test
    void testGetAllLearningPlansByTrainer() {
        // Given
        String trainer = "Test Trainer";
        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        when(learningPlanPathService.getAllLearningPlansByTrainer(trainer)).thenReturn(learningPlanPaths);

        // When
        ResponseEntity<?> responseEntity = learningPlanPathController.getAllLearningPlansByTrainer(trainer);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPaths, responseEntity.getBody());
        verify(learningPlanPathService, times(1)).getAllLearningPlansByTrainer(trainer);
    }

    @Test
    void testUpdateTrainer() {
        // Given
        Long pathId = 1L;
        String newTrainer = "New Trainer";

        // When
        ResponseEntity<?> responseEntity = learningPlanPathController.updateTrainer(pathId, newTrainer);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Trainer updated successfully", responseEntity.getBody());
        verify(learningPlanPathService, times(1)).updateTrainer(pathId, newTrainer);
    }

    @Test
    void testUpdateLearningPlanDates() {
        // Given
        Long learningPlanPathID = 1L;
        DateRange dateRange = new DateRange();
        dateRange.setStartDate(new Date());
        dateRange.setEndDate(new Date());

        when(learningPlanPathService.updateDates(learningPlanPathID, dateRange.getStartDate(), dateRange.getEndDate()))
                .thenReturn(Optional.of(new LearningPlanPath()));

        // When
        ResponseEntity<?> responseEntity = learningPlanPathController.updateLearningPlanDates(learningPlanPathID,
                dateRange);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Date Range updated successfully.", responseEntity.getBody());
        verify(learningPlanPathService, times(1)).updateDates(learningPlanPathID, dateRange.getStartDate(),
                dateRange.getEndDate());
    }
}
