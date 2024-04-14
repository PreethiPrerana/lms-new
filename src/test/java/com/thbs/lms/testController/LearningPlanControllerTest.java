package com.thbs.lms.testController;

import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.service.BulkUploadService;
import com.thbs.lms.service.LearningPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.thbs.lms.controller.LearningPlanController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningPlanControllerTest {

    @Mock
    private LearningPlanService learningPlanService;

    @Mock
    private BulkUploadService bulkUploadService;

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
    public void testSaveLearningPlan() {
        when(learningPlanService.saveLearningPlan(learningPlan)).thenReturn(learningPlan);

        ResponseEntity<?> responseEntity = learningPlanController.saveLearningPlan(learningPlan);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlan, responseEntity.getBody());
    }

    // @Test
    // public void testUploadFile() throws IOException {
    // MultipartFile file = null;

    // doNothing().when(bulkUploadService).uploadFile(file);

    // ResponseEntity<?> responseEntity = learningPlanController.uploadFile(file);

    // assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // assertEquals("File uploaded successfully.", responseEntity.getBody());
    // }

    @Test
    public void testUploadFile_Success() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.ms-excel",
                "test data".getBytes());

        doNothing().when(bulkUploadService).uploadFile(file);

        ResponseEntity<?> responseEntity = learningPlanController.uploadFile(file);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("File uploaded successfully.", responseEntity.getBody());
    }

    @Test
    public void testUploadFile_PdfFileUploadFailure() {
        // Mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test data".getBytes());

        // Call the controller method
        ResponseEntity<String> response = learningPlanController.uploadFile(file);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unsupported file format: PDF", response.getBody());
    }

    @Test
    public void testUploadFile_PngFileUploadFailure() {
        // Mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test data".getBytes());

        // Call the controller method
        ResponseEntity<String> response = learningPlanController.uploadFile(file);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unsupported file format: PNG", response.getBody());
    }

    @Test
    public void testUploadFile_TextFileUploadFailure() {
        // Mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

        // Call the controller method
        ResponseEntity<String> response = learningPlanController.uploadFile(file);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unsupported file format: TXT", response.getBody());
    }

    @Test
    public void testUploadFile_JpegFileUploadFailure() {
        // Mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.jpeg", "image/jpeg", "test data".getBytes());

        // Call the controller method
        ResponseEntity<String> response = learningPlanController.uploadFile(file);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unsupported file format: JPEG", response.getBody());
    }

    @Test
    public void testGetAllLearningPlans() {
        List<LearningPlan> learningPlans = new ArrayList<>();

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setType("Test Type 2");

        learningPlans.add(learningPlan);
        learningPlans.add(learningPlan2);

        when(learningPlanService.getAllLearningPlans()).thenReturn(learningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getAllLearningPlans();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlans, responseEntity.getBody());
    }

    @Test
    public void testFindByType() {
        String type = "Type";

        List<LearningPlan> learningPlans = new ArrayList<>();

        LearningPlan learningPlan1 = new LearningPlan();
        learningPlan1.setType("Type");

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setType("Type");

        learningPlans.add(learningPlan1);
        learningPlans.add(learningPlan2);

        when(learningPlanService.getLearningPlansByType(type)).thenReturn(learningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getLearningPlansByType(type);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlans, responseEntity.getBody());
    }

    @Test
    public void testFindByBatchID() {
        Long batchID = 123L;

        List<LearningPlan> learningPlans = new ArrayList<>();

        LearningPlan learningPlan1 = new LearningPlan();
        learningPlan1.setType("Type 1");

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setType("Type 2");

        learningPlans.add(learningPlan1);
        learningPlans.add(learningPlan2);

        when(learningPlanService.getLearningPlansByBatchID(batchID)).thenReturn(learningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getLearningPlansByBatchID(batchID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlans, responseEntity.getBody());
    }

    @Test
    public void testDeleteLearningPlan() {
        Long idToDelete = 1L;

        ResponseEntity<?> responseEntity = learningPlanController.deleteLearningPlan(idToDelete);

        verify(learningPlanService).deleteLearningPlan(idToDelete);
        assertEquals("LearningPlan deleted successfully.", responseEntity.getBody());
    }
}
