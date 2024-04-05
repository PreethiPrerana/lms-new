// package com.thbs.lms.serviceTest;

// import static org.mockito.Mockito.*;

// import java.io.ByteArrayInputStream;
// import java.io.IOException;

// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Workbook;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.web.multipart.MultipartFile;

// import com.thbs.lms.model.Course;
// import com.thbs.lms.model.Topic;
// import com.thbs.lms.repository.CourseRepository;
// import com.thbs.lms.repository.TopicRepository;
// import com.thbs.lms.service.BulkUploadService;

// public class BulkUploadServiceTest {

//     @Mock
//     private CourseRepository courseRepository;

//     @Mock
//     private TopicRepository topicRepository;

//     @InjectMocks
//     private BulkUploadService bulkUploadService;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testUploadFile() throws IOException {
//         // Mock MultipartFile
//         String content = "Level,INTERMEDIATE\n"
//                         + "Day 1: Introduction to Java,\"Introduction to programming language\n"
//                         + "OOPS concept\n"
//                         + "Abstraction and Inheritance\n"
//                         + "method overloading and overriding\"\n"
//                         + "Day 2: Hands-on with Java,\"Basic coding questions\n"
//                         + "Constructor\n"
//                         + "Polymorphism\"";
//         MultipartFile multipartFile = new MockMultipartFile("file.xlsx", new ByteArrayInputStream(content.getBytes()));

//         // Mock CourseRepository behavior
//         Course course = new Course();
//         course.setCourseName("Day 1: Introduction to Java");
//         course.setLevel("INTERMEDIATE");
//         when(courseRepository.findByCourseName("Day 1: Introduction to Java")).thenReturn(java.util.Optional.of(course));

//         // Mock TopicRepository behavior
//         when(topicRepository.saveAll(anyList())).thenReturn(null);

//         // Call the method under test
//         bulkUploadService.uploadFile(multipartFile);

//         // Verify interactions
//         verify(courseRepository, times(1)).findByCourseName("Day 1: Introduction to Java");
//         verify(courseRepository, times(1)).save(any(Course.class));
//         verify(topicRepository, times(1)).saveAll(anyList());
//     }
// }

