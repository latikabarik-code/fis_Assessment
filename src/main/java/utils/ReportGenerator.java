package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ReportGenerator {

    public static void generateCartReport(int cartCount) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cart Report");

        // To Create header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Report Summary");

        // To Create data row
        Row dataRow = sheet.createRow(2);
        dataRow.createCell(0).setCellValue("Number of products added to the cart");
        dataRow.createCell(1).setCellValue(cartCount);

       // generate Excel file
        try (FileOutputStream fileOut = new FileOutputStream("cart_report.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Excel report generated: cart_report.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


