package com.thbs.lms.testService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.thbs.lms.exceptionHandler.DuplicateTopicException;
import com.thbs.lms.exceptionHandler.FileProcessingException;
import com.thbs.lms.exceptionHandler.InvalidSheetFormatException;
import com.thbs.lms.exceptionHandler.NoTopicEntriesException;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.repository.TopicRepository;
import com.thbs.lms.service.BulkUploadService;
import com.thbs.lms.utility.DuplicateExcelFileGenerator;
import com.thbs.lms.utility.EmptyRowExcelFileGenerator;
import com.thbs.lms.utility.InvalidExcelFileGenerator;
import com.thbs.lms.utility.MockExcelFileGenerator;
import com.thbs.lms.utility.NewCourseExcelFileGenerator;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BulkUploadServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private BulkUploadService bulkUploadService;

    @Test
    public void testValidExcelFileUpload() throws IOException, FileProcessingException {
        MockMultipartFile file = MockExcelFileGenerator.generateMockExcelFile();
        // Call the uploadFile method and ensure no exceptions are thrown
        assertDoesNotThrow(() -> bulkUploadService.uploadFile(file));
    }

    @Test
    public void testInvalidSheetFormatUploadFailure() throws IOException {
        String filePath = "invalid.xlsx";
        MockMultipartFile file = InvalidExcelFileGenerator.generateInvalidExcelFile(filePath);        
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }
    
    @Test
    public void testHandlingDuplicateTopics() throws IOException {
        String filePath = "duplicate.xlsx";
        MockMultipartFile file = DuplicateExcelFileGenerator.generateDuplicateExcelFile(filePath);
        assertThrows(DuplicateTopicException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    public void testHandlingMissingTopics() throws IOException, FileProcessingException {
        MockMultipartFile file = EmptyRowExcelFileGenerator.generateEmptyRowExcelFile();
        assertThrows(NoTopicEntriesException.class, () -> bulkUploadService.uploadFile(file));
    }
 
    @Test
    public void testUploadFileWithNewCourse() throws IOException {
        // Generate and save the initial Excel file to a temporary location
        Path initialFilePath = Files.createTempFile("initial_excel", ".xlsx");
        MockMultipartFile initialFile = MockExcelFileGenerator.generateMockExcelFile();
        Files.write(initialFilePath, initialFile.getBytes());

        // Upload the initial Excel file
        // bulkUploadService.uploadFile(new MockMultipartFile("file", "initial_excel.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", Files.readAllBytes(initialFilePath)));
        bulkUploadService.uploadFile(initialFile);
        long initialCourseCount = courseRepository.count();

        // Generate and upload the new course Excel file
        MockMultipartFile newCourseFile = NewCourseExcelFileGenerator.generateNewCourseExcelFile();
        bulkUploadService.uploadFile(newCourseFile);
        long updatedCourseCount = courseRepository.count();

        assertEquals(initialCourseCount + 1, updatedCourseCount, "Number of courses should increase by one after uploading a file with a new course");
    }


    @Test
    public void testHandlingExistingCourseInDatabase() throws IOException, FileProcessingException {
        // Save the generated mock Excel file to a temporary location
        Path tempFilePath = Files.createTempFile("mock_excel", ".xlsx");
        MockMultipartFile file = MockExcelFileGenerator.generateMockExcelFile();
        Files.write(tempFilePath, file.getBytes());

        // Upload the file for the first time
        bulkUploadService.uploadFile(new MockMultipartFile("file", "mock_excel.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", Files.readAllBytes(tempFilePath)));

        long initialCourseCount = courseRepository.count();
        long initialTopicCount = topicRepository.count();

        // Upload the same file again
        bulkUploadService.uploadFile(new MockMultipartFile("file", "mock_excel.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", Files.readAllBytes(tempFilePath)));

        long finalCourseCount = courseRepository.count();
        long finalTopicCount = topicRepository.count();

        assertEquals(initialCourseCount, finalCourseCount, "Number of courses should remain unchanged");
        assertEquals(initialTopicCount, finalTopicCount, "Number of topics should remain unchanged");
    }

}
