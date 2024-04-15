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
import java.util.Set;

/**
 * Service class responsible for bulk uploading topics from Excel files.
 */

@Service
public class BulkUploadService {

    private final CourseRepository courseRepository;
    private final TopicRepository topicRepository;

    /**
     * Constructs a BulkUploadService with repositories for courses and topics.
     */
    @Autowired
    public BulkUploadService(CourseRepository courseRepository, TopicRepository topicRepository) {
        this.courseRepository = courseRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * Processes an Excel file, handling I/O errors and processing issues.
     */
    public void uploadFile(MultipartFile file) {
        try {

            Workbook workbook = WorkbookFactory.create(file.getInputStream());

            // Process each sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                SheetValidator.isValidSheetFormat(sheet);

                // Check if header row exists
                Row headerRow = sheet.getRow(0);
                String level = headerRow.getCell(1).getStringCellValue();

                // Extract course name from the sheet name
                String courseName = sheet.getSheetName();

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
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new FileProcessingException("Error processing the uploaded file.");
        }
    }

    /**
     * Processes topics from a sheet and associates them with a course, returning
     * the list of topics.
     */
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

                // Check for duplicate topic names in the set.
                if (topicNames.contains(topicName)) {
                    throw new DuplicateTopicException("Duplicate entries present in sheet."); // If found, throw an
                                                                                              // exception
                }
                topicNames.add(topicName); // otherwise, add to the set.

                Topic topic = new Topic(); // Create a new Topic with extracted data.
                topic.setTopicName(topicName); // Set topic name.
                topic.setDescription(description); // Set description.
                topic.setCourse(course); // Set associated course.
                topics.add(topic); // Add topic to the list.
                System.out.println("Topic:" + topic); // Print topic for debugging.
            } catch (DuplicateTopicException e) {
                throw e; // Re-throw DuplicateTopicException
            } catch (Exception e) {
                throw new InvalidSheetFormatException("Sheet may have extra cells or invalid data.");
            }
        }
        return topics;
    }

    /**
     * Checks if a row in an Excel sheet is empty.
     */
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                // Found a non-empty cell
                return false; // so the row is not empty
            }
        }
        return true; // All cells in the row are empty
    }
}