package utils;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;
    private static final Logger logger = LogManager.getLogger(ExcelUtils.class);

    // Load Excel file
    public static void loadExcel(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(fis);
            logger.info("Excel file loaded: " + filePath);
        } catch (IOException e) {
            logger.error("Error loading Excel file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
   // Get specific row as String[]
    public static String[] getRowData(String sheetName, int rowIndex) {
        sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowIndex);
        if (row == null) return new String[0];

        int cols = row.getLastCellNum();
        String[] rowData = new String[cols];
        DataFormatter formatter = new DataFormatter();

        for (int i = 0; i < cols; i++) {
            Cell cell = row.getCell(i);
            rowData[i] = formatter.formatCellValue(cell);
        }
        return rowData;
    }

    // Get cell value as string
    public static String getCellData(String sheetName, int rowIndex, int colIndex) {
        sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowIndex);
        if (row == null) return "";

        Cell cell = row.getCell(colIndex);
        if (cell == null) return "";

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    // Get row count
    public static int getRowCount(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum() + 1;
    }

    // Get all data from sheet as 2D String array
    public static String[][] getAllData(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        int totalRows = sheet.getLastRowNum() + 1;
        int totalCols = sheet.getRow(0).getLastCellNum();

        String[][] data = new String[totalRows - 1][totalCols];
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i < totalRows; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < totalCols; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = formatter.formatCellValue(cell);
            }
        }
        return data;
    }

    // Close workbook (optional cleanup)
    public static void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
                logger.info("Excel workbook closed.");
            }
        } catch (IOException e) {
            logger.error("Failed to close workbook: " + e.getMessage());
        }
    }
}
