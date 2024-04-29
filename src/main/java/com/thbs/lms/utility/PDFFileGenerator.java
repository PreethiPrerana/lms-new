package com.thbs.lms.utility;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The {@code PDFFileGenerator} class provides utility methods for generating
 * PDF files
 * and converting them into {@code MockMultipartFile} objects.
 */
public class PDFFileGenerator {
    // Private constructor to prevent instantiation
    private PDFFileGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a PDF file with sample content.
     *
     * @param filePath The path where the generated PDF file will be saved.
     * @return A {@code File} object representing the generated PDF file.
     * @throws IOException If an I/O error occurs.
     */
    public static File generatePDFFile(String filePath) throws IOException {
        File pdfFile = new File(filePath);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("This is a sample PDF file.");
                contentStream.endText();
            }

            document.save(pdfFile);
        }

        return pdfFile;
    }

    /**
     * Converts a PDF file into a {@code MockMultipartFile}.
     *
     * @param file The PDF file to be converted.
     * @return A {@code MockMultipartFile} representing the PDF file.
     * @throws IOException If an I/O error occurs.
     */
    public static MockMultipartFile convertToMockMultipartFile(File file) throws IOException {
        try (FileInputStream input = new FileInputStream(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            return new MockMultipartFile("file", file.getName(), "application/pdf", output.toByteArray());
        }
    }

}
