
package com.thbs.lms.testService;

import com.thbs.lms.exceptionHandler.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import com.thbs.lms.service.LearningPlanPathService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningPlanPathServiceTest {

    @Mock
    private LearningPlanPathRepository learningPlanPathRepository;

    @InjectMocks
    private LearningPlanPathService learningPlanPathService;

    private LearningPlanPath learningPlanPath;
    private Course course;
    private LearningPlan learningPlan;

    @BeforeEach
    void setUp() {
        learningPlanPath = new LearningPlanPath();
        learningPlanPath.setStartDate(new Date());
        learningPlanPath.setEndDate(new Date());
        learningPlanPath.setTrainer("Test Trainer");
        learningPlanPath.setType("Test Type");

        course = new Course();
        course.setCourseID(1L); // Set course ID

        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanID(1L); // Set learning plan ID

        learningPlanPath.setCourse(course);
        learningPlanPath.setLearningPlan(learningPlan);
    }

    @Test
    void testCreateLearningPlanPath_InvalidData() {
        // Set invalid data
        learningPlanPath.setStartDate(null);
        learningPlanPath.setEndDate(null);
        learningPlanPath.setTrainer("");
        learningPlanPath.setType("");
        learningPlanPath.setCourse(null);

        // Call the service method and expect InvalidLearningPlanPathDataException
        assertThrows(InvalidLearningPlanPathDataException.class, () -> learningPlanPathService.createLearningPlanPath(learningPlanPath));
    }

    @Test
    void testCreateLearningPlanPath_DuplicateEntry() {
        // Mock repository to return an existing entry
        when(learningPlanPathRepository.findByLearningPlanLearningPlanIDAndCourseAndType(any(LearningPlan.class).getLearningPlanID(), any(Course.class), anyString()))
                .thenReturn(Optional.of(learningPlanPath));

        // Call the service method and expect DuplicateLearningPlanPathException
        assertThrows(DuplicateLearningPlanPathException.class, () -> learningPlanPathService.createLearningPlanPath(learningPlanPath));
    }

    @Test
    void testCreateLearningPlanPath_Success() {
        // Mock repository to return no existing entry
        when(learningPlanPathRepository.findByLearningPlanLearningPlanIDAndCourseAndType(any(LearningPlan.class).getLearningPlanID(), any(Course.class), anyString()))
                .thenReturn(Optional.empty());
        // Mock repository to save the learning plan path
        when(learningPlanPathRepository.save(any(LearningPlanPath.class)))
                .thenReturn(learningPlanPath);

        // Call the service method
        LearningPlanPath result = learningPlanPathService.createLearningPlanPath(learningPlanPath);

        // Assert the result
        assertNotNull(result);
        assertEquals(learningPlanPath, result);
    }

    @Test
    void testGetAllLearningPlanPathsByLearningPlanId_Success() {
        // Mock repository to return a list of learning plan paths
        List<LearningPlanPath> expectedPaths = List.of(learningPlanPath);
        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(anyLong()))
                .thenReturn(expectedPaths);

        // Call the service method
        List<LearningPlanPath> actualPaths = learningPlanPathService.getAllLearningPlanPathsByLearningPlanId(1L);

        // Assert the result
        assertEquals(expectedPaths.size(), actualPaths.size());
    }

    @Test
    void testGetAllLearningPlansByType_Success() {
        // Mock repository to return a list of learning plan paths
        List<LearningPlanPath> expectedPaths = List.of(learningPlanPath);
        when(learningPlanPathRepository.findByType(anyString()))
                .thenReturn(expectedPaths);

        // Call the service method
        List<LearningPlanPath> actualPaths = learningPlanPathService.getAllLearningPlansByType("Test Type");

        // Assert the result
        assertEquals(expectedPaths.size(), actualPaths.size());
    }

    @Test
    void testGetAllLearningPlansByTrainer_Success() {
        // Mock repository to return a list of learning plan paths
        List<LearningPlanPath> expectedPaths = List.of(learningPlanPath);
        when(learningPlanPathRepository.findByTrainer(anyString()))
                .thenReturn(expectedPaths);

        // Call the service method
        List<LearningPlanPath> actualPaths = learningPlanPathService.getAllLearningPlansByTrainer("Test Trainer");

        // Assert the result
        assertEquals(expectedPaths.size(), actualPaths.size());
    }

    @Test
    void testUpdateTrainer_Success() {
        // Mock repository to return an optional learning plan path
        when(learningPlanPathRepository.findById(anyLong()))
                .thenReturn(Optional.of(learningPlanPath));

        // Call the service method
        learningPlanPathService.updateTrainer(1L, "New Trainer");

        // Assert the updated trainer
        assertEquals("New Trainer", learningPlanPath.getTrainer());
    }

    @Test
    void testUpdateTrainer_InvalidTrainer() {
        // Call the service method with invalid trainer and expect InvalidTrainerException
        assertThrows(InvalidTrainerException.class, () -> learningPlanPathService.updateTrainer(1L, ""));
    }

    @Test
    void testUpdateTrainer_LearningPlanPathNotFound() {
        // Mock repository to return an empty optional (learning plan path not found)
        when(learningPlanPathRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Call the service method and expect a LearningPlanPathNotFoundException
        assertThrows(LearningPlanPathNotFoundException.class, () -> learningPlanPathService.updateTrainer(1L, "New Trainer"));
    }

    @Test
    void testUpdateDates_Success() {
        // Mock repository to return an optional learning plan path
        when(learningPlanPathRepository.findById(anyLong()))
                .thenReturn(Optional.of(learningPlanPath));

        // Call the service method
        Optional<LearningPlanPath> updatedPath = learningPlanPathService.updateDates(1L, new Date(), new Date());

        // Assert the result
        assertTrue(updatedPath.isPresent());
    }

    @Test
    void testUpdateDates_InvalidData() {
        // Call the service method with invalid dates and expect InvalidLearningPlanPathDataException
        assertThrows(InvalidLearningPlanPathDataException.class, () -> learningPlanPathService.updateDates(1L, null, null));
    }

    @Test
    void testUpdateDates_EndDateBeforeStartDate() {
        // Call the service method with end date before start date and expect InvalidLearningPlanPathDataException
        assertThrows(InvalidLearningPlanPathDataException.class, () -> learningPlanPathService.updateDates(1L, new Date(), new Date(new Date().getTime() - 1000)));
    }

    @Test
    void testUpdateDates_LearningPlanPathNotFound() {
        // Mock repository to return an empty optional (learning plan path not found)
        when(learningPlanPathRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Call the service method and expect a LearningPlanPathNotFoundException
        assertThrows(LearningPlanPathNotFoundException.class, () -> learningPlanPathService.updateDates(1L, new Date(), new Date()));
    }
}
