package com.thbs.lms.testService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.thbs.lms.exceptionHandler.DuplicateLearningPlanPathException;
import com.thbs.lms.exceptionHandler.InvalidLearningPlanPathDataException;
import com.thbs.lms.exceptionHandler.InvalidTrainerException;
import com.thbs.lms.exceptionHandler.InvalidTypeException;
import com.thbs.lms.exceptionHandler.LearningPlanPathNotFoundException;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import com.thbs.lms.service.LearningPlanPathService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LearningPlanPathServiceTest {

    @Autowired
    private LearningPlanPathService learningPlanPathService;

    @MockBean
    private LearningPlanPathRepository learningPlanPathRepository;

    private LearningPlanPath learningPlanPath;
    private Course course;
    private LearningPlan learningPlan;

    @BeforeEach
    public void setUp() {
        // Mocking a LearningPlanPath object
        course = new Course();
        course.setCourseID(1L);
        course.setCourseName("Test Course");

        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanID(1L);

        learningPlanPath = new LearningPlanPath();
        learningPlanPath.setPathID(1L);
        learningPlanPath.setStartDate(new Date());
        learningPlanPath.setEndDate(new Date());
        learningPlanPath.setTrainer("Test Trainer");
        learningPlanPath.setType("Test Type");
        learningPlanPath.setCourse(course);
        learningPlanPath.setLearningPlan(learningPlan);
    }

    @Test
    public void testCreateLearningPlanPath_ValidInput() {
        when(learningPlanPathRepository.save(any(LearningPlanPath.class))).thenReturn(learningPlanPath);

        LearningPlanPath createdPath = learningPlanPathService.createLearningPlanPath(learningPlanPath);

        assertNotNull(createdPath);
        assertEquals(learningPlanPath.getTrainer(), createdPath.getTrainer());
    }

    @Test
    public void testCreateLearningPlanPath_InvalidInput() {
        // Making start date null to trigger invalid data condition
        learningPlanPath.setStartDate(null);

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.createLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    public void testCreateLearningPlanPath_DuplicateEntry() {
        when(learningPlanPathRepository.findByLearningPlanLearningPlanIDAndCourseAndType(anyLong(), any(Course.class), anyString())).thenReturn(Optional.of(learningPlanPath));

        assertThrows(DuplicateLearningPlanPathException.class, () -> {
            learningPlanPathService.createLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    public void testSaveAllLearningPlanPaths_ValidInputList() {
        // Mocking saveAll method of repository
        when(learningPlanPathRepository.saveAll(anyList())).thenReturn(Arrays.asList(learningPlanPath));

        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();
        learningPlanPaths.add(learningPlanPath);

        List<LearningPlanPath> savedPaths = learningPlanPathService.saveAllLearningPlanPaths(learningPlanPaths);

        assertNotNull(savedPaths);
        assertEquals(1, savedPaths.size());
        assertEquals(learningPlanPath.getPathID(), savedPaths.get(0).getPathID());
    }

    @Test
    public void testSaveAllLearningPlanPaths_EmptyList() {
        // Mocking saveAll method of repository
        when(learningPlanPathRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        List<LearningPlanPath> savedPaths = learningPlanPathService.saveAllLearningPlanPaths(new ArrayList<>());

        assertNotNull(savedPaths);
        assertEquals(0, savedPaths.size());
    }

    @Test
    public void testGetAllLearningPlanPathsByLearningPlanId_ExistingId() {
        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(anyLong())).thenReturn(Arrays.asList(learningPlanPath));

        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByLearningPlanId(1L);

        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(learningPlanPath.getPathID(), paths.get(0).getPathID());
    }

    @Test
    public void testGetAllLearningPlanPathsByLearningPlanId_NonExistentId() {
        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(anyLong())).thenReturn(new ArrayList<>());

        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByLearningPlanId(2L);

        assertNotNull(paths);
        assertEquals(0, paths.size());
    }

    @Test
    public void testGetAllLearningPlanPathsByType_ValidType() {
        when(learningPlanPathRepository.findByType(anyString())).thenReturn(Arrays.asList(learningPlanPath));

        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByType("Test Type");

        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(learningPlanPath.getPathID(), paths.get(0).getPathID());
    }

    @Test
    public void testGetAllLearningPlanPathsByType_NullType() {
        assertThrows(InvalidTypeException.class, () -> {
            learningPlanPathService.getAllLearningPlanPathsByType(null);
        });
    }

    @Test
    public void testGetAllLearningPlanPathsByTrainer_ValidTrainer() {
        when(learningPlanPathRepository.findByTrainer(anyString())).thenReturn(Arrays.asList(learningPlanPath));

        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByTrainer("Test Trainer");

        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(learningPlanPath.getPathID(), paths.get(0).getPathID());
    }

    @Test
    public void testGetAllLearningPlanPathsByTrainer_NullTrainer() {
        assertThrows(InvalidTrainerException.class, () -> {
            learningPlanPathService.getAllLearningPlanPathsByTrainer(null);
        });
    }

    @Test
    public void testUpdateLearningPlanPathTrainer_ValidInputs() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.of(learningPlanPath));
        when(learningPlanPathRepository.save(any(LearningPlanPath.class))).thenReturn(learningPlanPath);

        String newTrainer = "New Trainer";
        learningPlanPathService.updateLearningPlanPathTrainer(1L, newTrainer);

        assertEquals(newTrainer, learningPlanPath.getTrainer());
    }

    @Test
    public void testUpdateLearningPlanPathTrainer_NullTrainer() {
        assertThrows(InvalidTrainerException.class, () -> {
            learningPlanPathService.updateLearningPlanPathTrainer(1L, null);
        });
    }

    @Test
    public void testUpdateLearningPlanPathTrainer_NonExistentPathId() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LearningPlanPathNotFoundException.class, () -> {
            learningPlanPathService.updateLearningPlanPathTrainer(2L, "New Trainer");
        });
    }

    @Test
    public void testUpdateLearningPlanPathDates_ValidInputs() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.of(learningPlanPath));
        when(learningPlanPathRepository.save(any(LearningPlanPath.class))).thenReturn(learningPlanPath);

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 1000 * 60 * 60 * 24); // Adding one day

        Optional<LearningPlanPath> updatedPath = learningPlanPathService.updateLearningPlanPathDates(1L, startDate, endDate);

        assertTrue(updatedPath.isPresent());
        assertEquals(startDate, updatedPath.get().getStartDate());
        assertEquals(endDate, updatedPath.get().getEndDate());
    }

    @Test
    public void testUpdateLearningPlanPathDates_NullDates() {
        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.updateLearningPlanPathDates(1L, null, null);
        });
    }

    @Test
    public void testUpdateLearningPlanPathDates_EndDateBeforeStartDate() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() - 1000 * 60 * 60 * 24); // Subtracting one day

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.updateLearningPlanPathDates(1L, startDate, endDate);
        });
    }

    @Test
    public void testUpdateLearningPlanPathDates_NonExistentPathId() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LearningPlanPathNotFoundException.class, () -> {
            learningPlanPathService.updateLearningPlanPathDates(2L, new Date(), new Date());
        });
    }

    @Test
    public void testDeleteLearningPlanPathsByLearningPlanId_ExistingId() {
        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(anyLong())).thenReturn(Arrays.asList(learningPlanPath));

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPathsByLearningPlanId(1L);
        });
    }

    @Test
    public void testDeleteLearningPlanPathsByLearningPlanId_NonExistentId() {
        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(anyLong())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPathsByLearningPlanId(2L);
        });
    }

    @Test
    public void testDeleteLearningPlanPaths_ValidList() {
        List<LearningPlanPath> paths = Arrays.asList(learningPlanPath);

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPaths(paths);
        });
    }

    @Test
    public void testDeleteLearningPlanPaths_EmptyList() {
        List<LearningPlanPath> paths = new ArrayList<>();

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPaths(paths);
        });
    }

    @Test
    public void testDeleteLearningPlanPath_ExistingPathId() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.of(learningPlanPath));

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPath(1L);
        });
    }

    @Test
    public void testDeleteLearningPlanPath_NonExistentPathId() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LearningPlanPathNotFoundException.class, () -> {
            learningPlanPathService.deleteLearningPlanPath(2L);
        });
    }
}
