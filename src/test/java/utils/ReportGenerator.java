package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportGenerator {

    public static void generateExcelReport(String fileName, String reportTitle, Map<String, Object> reportData) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        // To Create title row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(reportTitle);

        // To Create header row
        Row headerRow = sheet.createRow(2);
        headerRow.createCell(0).setCellValue("Field");
        headerRow.createCell(1).setCellValue("Value");

        // To Fill data rows
        int rowNum = 3;
        for (Map.Entry<String, Object> entry : reportData.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            if (entry.getValue() != null) {
                row.createCell(1).setCellValue(entry.getValue().toString());
            } else {
                row.createCell(1).setCellValue("null");
            }
        }

        // To Autosize columns
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            System.out.println("Excel report generated: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //To generate add to cart Report
    public static void generateCartReport(int cartCount, String fileName) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("Number of products added to the cart", cartCount);
        generateExcelReport(fileName, "Cart Report Summary", data);
    }

    // To generate bitcoin Report
    public static void generateBitcoinReport(Map<String, Object> bitcoinData, String fileName) {
        generateExcelReport(fileName, "Bitcoin API Report Summary", bitcoinData);
    }
}


