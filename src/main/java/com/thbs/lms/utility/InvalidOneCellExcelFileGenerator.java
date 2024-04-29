package com.thbs.lms.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The {@code InvalidOneCellExcelFileGenerator} class provides a utility method
 * to generate a mock Excel file
 * with only one cell for testing purposes.
 */
public class InvalidOneCellExcelFileGenerator {
    // Private constructor to prevent instantiation
    private InvalidOneCellExcelFileGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a mock Excel file with only one cell.
     *
     * @param filePath The file path of the generated Excel file. (Not used in the
     *                 method implementation)
     * @return A {@code MockMultipartFile} representing the generated Excel file.
     * @throws IOException If an I/O error occurs.
     */
    public static MockMultipartFile generateInvalidOneCellExcelFile(String filePath) throws IOException {
        // Create a new workbook
        try (Workbook workbook = new XSSFWorkbook()) {

            // Create a new sheet
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Level");

            // Create data rows
            Row dataRow1 = sheet.createRow(1);
            dataRow1.createCell(0).setCellValue("Data1");

            Row dataRow2 = sheet.createRow(2);
            dataRow2.createCell(0).setCellValue("Data2");

            // Write workbook content to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] content = outputStream.toByteArray();

            // Create MockMultipartFile
            return new MockMultipartFile(
                    "file",
                    "mock_excel.xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    content);
        }
    }
}
