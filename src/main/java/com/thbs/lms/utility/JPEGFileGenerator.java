package com.thbs.lms.utility;

import javax.imageio.ImageIO;

import org.springframework.mock.web.MockMultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The {@code JPEGFileGenerator} class provides utility methods to generate a
 * JPEG file
 * and convert it into a {@code MockMultipartFile} for testing purposes.
 */
public class JPEGFileGenerator {
    // Private constructor to prevent instantiation
    private JPEGFileGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a JPEG file.
     *
     * @param filePath The file path where the JPEG file will be generated.
     * @return A {@code File} representing the generated JPEG file.
     * @throws IOException If an I/O error occurs.
     */
    public static File generateJPEGFile(String filePath) throws IOException {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        File jpegFile = new File(filePath);
        ImageIO.write(image, "jpeg", jpegFile);
        return jpegFile;
    }

    /**
     * Converts a JPEG file into a {@code MockMultipartFile}.
     *
     * @param file The JPEG file to be converted.
     * @return A {@code MockMultipartFile} representing the converted JPEG file.
     * @throws IOException If an I/O error occurs.
     */
    public static MockMultipartFile convertToMockMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(input);
        ImageIO.write(image, "jpeg", output);
        return new MockMultipartFile("file", file.getName(), "image/jpeg", output.toByteArray());
    }
}
