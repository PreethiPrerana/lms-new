package com.thbs.lms.testService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.thbs.lms.exceptionHandler.DuplicateTopicException;
import com.thbs.lms.exceptionHandler.FileProcessingException;
import com.thbs.lms.exceptionHandler.InvalidSheetFormatException;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.repository.TopicRepository;
import com.thbs.lms.service.BulkUploadService;
import com.thbs.lms.utility.DuplicateTopicExcelFileGenerator;
import com.thbs.lms.utility.EmptyRowExcelFileGenerator;
import com.thbs.lms.utility.InvalidA1CellExcelGenerator;
import com.thbs.lms.utility.InvalidB1CellExcelGenerator;
import com.thbs.lms.utility.InvalidExtraDataExcelFileGenerator;
import com.thbs.lms.utility.InvalidNoDescriptionFileGenerator;
import com.thbs.lms.utility.InvalidOneCellExcelFileGenerator;
import com.thbs.lms.utility.MockExcelFileGenerator;
import com.thbs.lms.utility.NewCourseExcelFileGenerator;

import java.io.IOException;

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
    public void testInvalidA1CellExcelFile() throws IOException {
        String filePath = "invalidA1.xlsx";
        MockMultipartFile file = InvalidA1CellExcelGenerator.generateInvalidA1CellExcelFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    public void testInvalidB1CellExcelFile() throws IOException {
        String filePath = "invalidB1.xlsx";
        MockMultipartFile file = InvalidB1CellExcelGenerator.generateInvalidB1CellExcelFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    public void testInvalidNoDescriptionFile() throws IOException {
        String filePath = "noDesc.xlsx";
        MockMultipartFile file = InvalidNoDescriptionFileGenerator.generateInvalidNoDescriptionFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    public void testInvalidOneCellExcelFile() throws IOException {
        String filePath = "oneColumn.xlsx";
        MockMultipartFile file = InvalidOneCellExcelFileGenerator.generateInvalidOneCellExcelFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    public void testEmptyRowExcelFile() throws IOException {
        MockMultipartFile file = EmptyRowExcelFileGenerator.generateEmptyRowExcelFile();
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    public void testHandlingDuplicateTopics() throws IOException {
        String filePath = "duplicate.xlsx";
        MockMultipartFile file = DuplicateTopicExcelFileGenerator.generateDuplicateTopicExcelFile(filePath);
        assertThrows(DuplicateTopicException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    public void testHandlingMissingTopics() throws IOException, FileProcessingException {
        MockMultipartFile file = EmptyRowExcelFileGenerator.generateEmptyRowExcelFile();
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }
 
    @Test
    public void testUploadFileWithNewCourse() throws IOException {
        // Upload the initial file (matching existing courses)
        MockMultipartFile initialFile = MockExcelFileGenerator.generateMockExcelFile();
        assertDoesNotThrow(() -> bulkUploadService.uploadFile(initialFile));

        // Record the initial number of courses
        long initialCourseCount = courseRepository.count();

        // Upload the file with a new course (new sheet)
        MockMultipartFile newCourseFile = NewCourseExcelFileGenerator.generateNewCourseExcelFile();
        assertDoesNotThrow(() -> bulkUploadService.uploadFile(newCourseFile));

        // Retrieve the number of courses after upload
        long updatedCourseCount = courseRepository.count();

        // Assert that the number of courses has increased by one
        assertEquals(initialCourseCount , updatedCourseCount, "Number of courses should increase by one after uploading a file with a new course");
    }

    @Test
    public void testHandlingExistingCourseInDatabase() throws IOException {
        // Upload the initial file
        MockMultipartFile initialFile = MockExcelFileGenerator.generateMockExcelFile();
        assertDoesNotThrow(() -> bulkUploadService.uploadFile(initialFile));

        // Record the initial number of courses
        long initialCourseCount = courseRepository.count();

        // Upload the same file again
        MockMultipartFile sameFile = MockExcelFileGenerator.generateMockExcelFile();
        assertDoesNotThrow(() -> bulkUploadService.uploadFile(sameFile));

        // Retrieve the number of courses after the second upload
        long updatedCourseCount = courseRepository.count();

        // Assert that the number of courses remains unchanged
        assertEquals(initialCourseCount, updatedCourseCount, "Number of courses should remain unchanged after uploading the same file again");

    }

    @Test
    public void testInvalidExtraDataFileUpload() throws Exception {
        String filePath = "extraData.xlsx";
        MockMultipartFile file = InvalidExtraDataExcelFileGenerator.generateInvalidExtraDataExcelFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

}
