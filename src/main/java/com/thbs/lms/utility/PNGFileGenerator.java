package com.thbs.lms.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.mock.web.MockMultipartFile;

/**
 * The {@code PNGFileGenerator} class provides utility methods for generating
 * PNG files
 * and converting them into {@code MockMultipartFile} objects.
 */
public class PNGFileGenerator {
    // Private constructor to prevent instantiation
    private PNGFileGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a PNG file with sample content.
     *
     * @param filePath The path where the generated PNG file will be saved.
     * @return A {@code File} object representing the generated PNG file.
     * @throws IOException If an I/O error occurs.
     */
    public static File generatePNGFile(String filePath) throws IOException {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        File pngFile = new File(filePath);
        ImageIO.write(image, "png", pngFile);
        return pngFile;
    }

    /**
     * Converts a PNG file into a {@code MockMultipartFile}.
     *
     * @param file The PNG file to be converted.
     * @return A {@code MockMultipartFile} representing the PNG file.
     * @throws IOException If an I/O error occurs.
     */
    public static MockMultipartFile convertToMockMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(input);
        ImageIO.write(image, "png", output);
        return new MockMultipartFile("file", file.getName(), "image/png", output.toByteArray());
    }
}
