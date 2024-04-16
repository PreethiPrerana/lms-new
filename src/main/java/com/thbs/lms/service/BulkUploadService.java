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

import com.thbs.lms.exception.DuplicateTopicException;
import com.thbs.lms.exception.FileProcessingException;
import com.thbs.lms.exception.InvalidSheetFormatException;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.repository.TopicRepository;
import com.thbs.lms.utility.SheetValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BulkUploadService {

    private final CourseRepository courseRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public BulkUploadService(CourseRepository courseRepository, TopicRepository topicRepository) {
        this.courseRepository = courseRepository;
        this.topicRepository = topicRepository;
    }

    public void uploadFile(MultipartFile file) {
        System.out.println("uploadFile() method called.");
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            // Process each sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);

                SheetValidator.isValidSheetFormat(sheet);
                // Check if header row exists
                Row headerRow = sheet.getRow(0);

                String level = headerRow.getCell(1).getStringCellValue();
                // Extract course name from the sheet name
                String courseName = sheet.getSheetName();

                // Check if the course already exists in the database
                Optional<Course> existingCourseOptional = courseRepository.findByCourseNameIgnoreCase(courseName);
                Course course;
                if (existingCourseOptional.isPresent()) {
                    course = existingCourseOptional.get();
                    System.out.println("Course: " + course.getCourseName() + " already present.");
                } else {
                    // Create a new course and save it to the database
                    Course newCourse = new Course();
                    newCourse.setCourseName(courseName);
                    newCourse.setLevel(level);
                    System.out.println("New Course: " + newCourse.getCourseName());
                    course = courseRepository.save(newCourse);
                    System.out.println("Course saved!");
                }

                List<Topic> topics = processTopics(sheet, course);

                topicRepository.saveAll(topics);
                System.out.println("Topics saved!");
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new FileProcessingException("Error processing the uploaded file.");
        }
    }

    private List<Topic> processTopics(Sheet sheet, Course course) {
        List<Topic> topics = new ArrayList<>();
        Set<String> topicNames = new HashSet<>();
        Iterator<Row> iterator = sheet.iterator();

        // Skip header row
        if (iterator.hasNext()) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            if (isRowEmpty(currentRow)) {
                continue;
            }
            try {
                String topicName = currentRow.getCell(0).getStringCellValue();
                String description = currentRow.getCell(1).getStringCellValue();

                // Check if the topic already exists in the course
                if (topicRepository.existsByTopicNameAndCourse(topicName, course)) {
                    continue; // Skip adding existing topics
                }
                System.out.println("check duplicate");
                if (topicNames.contains(topicName)) {
                    System.out.println("Duplicate entries present.");
                    throw new DuplicateTopicException("Duplicate entries present in sheet.");
                }
                topicNames.add(topicName);

                Topic topic = new Topic();
                topic.setTopicName(topicName);
                topic.setDescription(description);
                topic.setCourse(course);
                topics.add(topic);
                System.out.println("Topic:" + topic.getTopicName());
            } catch (DuplicateTopicException e) {
                throw e; // Re-throw DuplicateTopicException
            } catch (Exception e) {
                throw new InvalidSheetFormatException("Sheet may have extra cells or invalid data.");
            }
        }
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