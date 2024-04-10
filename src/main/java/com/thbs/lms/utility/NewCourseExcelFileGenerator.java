package com.thbs.lms.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NewCourseExcelFileGenerator {
    
    public static MockMultipartFile generateNewCourseExcelFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        // Create the first sheet for an existing course
        Sheet sheet = workbook.createSheet("Sheet1");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Level");
        headerRow.createCell(1).setCellValue("BASIC");
        
        // Create data rows
        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("Data1");
        dataRow1.createCell(1).setCellValue("Data1Desc");
        
        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("Data2");
        dataRow2.createCell(1).setCellValue("Data2Desc");

        // Create the second sheet for the new course
        Sheet newCourseSheet = workbook.createSheet("NewCourse");
        Row newCourseHeaderRow = newCourseSheet.createRow(0);
        newCourseHeaderRow.createCell(0).setCellValue("Level");
        newCourseHeaderRow.createCell(1).setCellValue("INTERMEDIATE");
        // Add some data for the new course
        Row newCourseDataRow1 = newCourseSheet.createRow(1);
        newCourseDataRow1.createCell(0).setCellValue("New Topic 1");
        newCourseDataRow1.createCell(1).setCellValue("New Description 1");
        Row newCourseDataRow2 = newCourseSheet.createRow(2);
        newCourseDataRow2.createCell(0).setCellValue("New Topic 2");
        newCourseDataRow2.createCell(1).setCellValue("New Description 2");

        // Write workbook content to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] content = outputStream.toByteArray();

        // Create MockMultipartFile
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "mock_excel.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                content);

        // Close the workbook
        workbook.close();

        return file;
    }
}
