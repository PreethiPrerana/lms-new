package com.thbs.lms.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NewTopicExcelFileGenerator {

    public static MockMultipartFile generateNewTopicExcelFile() throws IOException {
        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a new sheet
        Sheet sheet = workbook.createSheet("Sheet1");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Level");
        headerRow.createCell(1).setCellValue("BASIC");

        // Create data rows
        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("Data3");
        dataRow1.createCell(1).setCellValue("Data3Desc");

        // Write workbook content to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] content = outputStream.toByteArray();

        // Create MockMultipartFile
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "new_topic_excel.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                content);

        // Close the workbook
        workbook.close();

        return file;
    }
}
