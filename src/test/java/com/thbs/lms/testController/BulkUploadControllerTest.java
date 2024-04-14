// package com.thbs.lms.testController;

// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import com.thbs.lms.utility.MockExcelFileGenerator;
// import com.thbs.lms.controller.LearningPlanController;
// import com.thbs.lms.exception.FileProcessingException;
// import com.thbs.lms.service.BulkUploadService;
// import com.thbs.lms.utility.JPEGFileGenerator;
// import com.thbs.lms.utility.PDFFileGenerator;
// import com.thbs.lms.utility.PNGFileGenerator;
// import com.thbs.lms.utility.TextFileGenerator;

// import java.io.File;
// import java.io.IOException;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.doNothing;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest
// @AutoConfigureMockMvc
// class BulkUploadControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Test
//     void testFileUploadSuccess() throws Exception {
//         MockMultipartFile file = MockExcelFileGenerator.generateMockExcelFile();

//         mockMvc.perform(MockMvcRequestBuilders.multipart("/learningplans/upload").file(file))
//                 .andExpect(status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().string("File uploaded successfully."));
//     }

//     @Test
//     void pdfFileUploadFailure() throws Exception {
//         String filePath = "sample.pdf";
//         File pdfFile = PDFFileGenerator.generatePDFFile(filePath);

//         MockMultipartFile file = PDFFileGenerator.convertToMockMultipartFile(pdfFile);

//         mockMvc.perform(MockMvcRequestBuilders.multipart("/learningplans/upload").file(file))
//                 .andExpect(status().isInternalServerError())
//                 .andExpect(MockMvcResultMatchers.content()
//                         .string("Error processing file: Can't open workbook - unsupported file type: PDF"));
//     }

//     @Test
//     void pngFileUploadFailure() throws Exception {
//         String filePath = "sample.png";
//         File pngFile = PNGFileGenerator.generatePNGFile(filePath);

//         MockMultipartFile file = PNGFileGenerator.convertToMockMultipartFile(pngFile);

//         mockMvc.perform(MockMvcRequestBuilders.multipart("/learningplans/upload").file(file))
//                 .andExpect(status().isInternalServerError())
//                 .andExpect(MockMvcResultMatchers.content()
//                         .string("Error processing file: Can't open workbook - unsupported file type: PNG"));
//     }

//     @Test
//     void textFileUploadFailure() throws Exception {
//         String filePath = "sample.txt";
//         File textFile = TextFileGenerator.generateTextFile(filePath);

//         MockMultipartFile file = TextFileGenerator.convertToMockMultipartFile(textFile);

//         mockMvc.perform(MockMvcRequestBuilders.multipart("/learningplans/upload").file(file))
//                 .andExpect(status().isInternalServerError())
//                 .andExpect(MockMvcResultMatchers.content()
//                         .string("Error processing file: Can't open workbook - unsupported file type: UNKNOWN"));
//     }

//     @Test
//     void jpegFileUploadFailure() throws Exception {
//         String filePath = "sample.jpeg";
//         File jpegFile = JPEGFileGenerator.generateJPEGFile(filePath);

//         MockMultipartFile file = JPEGFileGenerator.convertToMockMultipartFile(jpegFile);

//         mockMvc.perform(MockMvcRequestBuilders.multipart("/learningplans/upload").file(file))
//                 .andExpect(status().isInternalServerError())
//                 .andExpect(MockMvcResultMatchers.content()
//                         .string("Error processing file: Can't open workbook - unsupported file type: JPEG"));
//     }


// }