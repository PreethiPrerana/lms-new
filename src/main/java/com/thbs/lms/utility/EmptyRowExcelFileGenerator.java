package com.thbs.lms.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The {@code EmptyRowExcelFileGenerator} class provides a utility method to
 * generate a mock Excel file
 * with an empty row for testing purposes.
 */
public class EmptyRowExcelFileGenerator {
    // Private constructor to prevent instantiation
    private EmptyRowExcelFileGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a mock Excel file with an empty row.
     *
     * @return A {@code MockMultipartFile} representing the generated Excel file.
     * @throws IOException If an I/O error occurs.
     */
    public static MockMultipartFile generateEmptyRowExcelFile() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a new sheet
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Level");
            headerRow.createCell(1).setCellValue("BASIC");

            // Write workbook content to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] content = outputStream.toByteArray();

            // Create MockMultipartFile
            return new MockMultipartFile(
                    "file",
                    "empty_row_excel.xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    content);
        }

    }
}
