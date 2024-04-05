package com.thbs.lms.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thbs.lms.exception.FileProcessingException;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.repository.TopicRepository;

// import static org.mockito.Mockito.description;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BulkUploadService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    public void uploadFile(MultipartFile file) throws IOException, FileProcessingException {
        System.out.println("Inside upload file");
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        System.out.println("Exception not thrown.");

        // Process each sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            
            // Extract level from the first row, first column (assuming it's a header row)
            Row headerRow = sheet.getRow(0);
            String level = headerRow.getCell(1).getStringCellValue();
            System.out.println("level:"+level);
            // Extract course name from the sheet name
            String courseName = sheet.getSheetName();
            System.out.println("Course:"+courseName);
            // Create or get the course from the database
            Course course = courseRepository.findByCourseName(courseName)
                                .orElseGet(() -> {
                                    Course newCourse = new Course();
                                    newCourse.setCourseName(courseName);
                                    newCourse.setLevel(level);
                                    return courseRepository.save(newCourse);
                                });

            List<Topic> topics = processTopics(sheet, course);

            topicRepository.saveAll(topics);
        }
    }

    private List<Topic> processTopics(Sheet sheet, Course course) {
        System.out.println("Inside process topics");
        List<Topic> topics = new ArrayList<>();
        Iterator<Row> iterator = sheet.iterator();
        
        // Skip header row
        if (iterator.hasNext()) {
            iterator.next();
        }
        System.out.println("Iterator:"+iterator);
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            if (isRowEmpty(currentRow)) {
                continue;
            }
            String topicName = currentRow.getCell(0).getStringCellValue();
            String description = currentRow.getCell(1).getStringCellValue();

            boolean topicExists = topicRepository.existsByCourseAndTopicNameAndDescription(course, topicName, description);
            if (!topicExists) {
                Topic topic = new Topic();
                topic.setTopicName(topicName);
                topic.setDescription(description);
                topic.setCourse(course);
                topics.add(topic);
                System.out.println("Topic:" + topic);
            }
        }
        System.out.println("All Topics-:"+ topics);

        return topics;
    }
      private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                // Found a non-empty cell, so the row is not empty
                return false;
            }
        }
        // All cells in the row are empty
        return true;
    }
}
