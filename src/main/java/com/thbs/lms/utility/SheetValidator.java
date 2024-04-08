package com.thbs.lms.utility;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class SheetValidator {
    public static boolean isValidSheetFormat(Sheet sheet) {
        // Check if the sheet has at least one row
        if (sheet.getPhysicalNumberOfRows() < 1) {
            return false;
        }
    
        // Check if the first row (header row) has the correct format
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return false;
        }
        System.out.println("header row");
        
        // Check if the first cell (A1) contains "Level"
        Cell firstCell = headerRow.getCell(0);
        if (firstCell == null || !firstCell.getStringCellValue().trim().equalsIgnoreCase("Level")) {
            return false;
        }
        System.out.println("Cell A1: "+ firstCell);
    
        // Check if the second cell (B1) contains "BASIC", "INTERMEDIATE", or "ADVANCED"
        Cell levelCell = headerRow.getCell(1);
        if (levelCell == null) {
            return false;
        }
        System.out.println("Cell B1: "+ levelCell);
    
        String level = levelCell.getStringCellValue().trim();
        if (!level.equalsIgnoreCase("BASIC") && !level.equalsIgnoreCase("INTERMEDIATE") && !level.equalsIgnoreCase("ADVANCED")) {
            return false;
        }
        System.out.println("Valid B1 cell");
    
        // Check if the sheet contains at least two columns
        if (sheet.getRow(0).getPhysicalNumberOfCells() < 2) {
            return false;
        }
    
        // Get iterator for the rows in the sheet
        Iterator<Row> rowIterator = sheet.iterator();
        // Skip the header row
        rowIterator.next();
        // Process each subsequent row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            System.out.println("Row: " + row.getRowNum());
            Cell topicNameCell = row.getCell(0);
            Cell topicDescriptionCell = row.getCell(1);
            if (topicNameCell == null || topicDescriptionCell == null ||
                    topicNameCell.getCellType() == CellType.BLANK || topicDescriptionCell.getCellType() == CellType.BLANK) {
                break;
            }
            System.out.println("TopicName: " + topicNameCell);
            System.out.println("Description: " + topicDescriptionCell);
        }
        System.out.println("Outside while loop");
        return true;
    }
}
