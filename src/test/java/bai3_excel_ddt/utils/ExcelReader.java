package bai3_excel_ddt.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    /**
     * Đọc tệp tin excel và nạp hết vào Object[][] dùng cho DataProvider.
     * @param filePath Đường dẫn file
     * @param sheetName Tên Sheet Excel cần trích xuất
     * @return Data cung cấp array cho parameters
     */
    public static Object[][] getData(String filePath, String sheetName) {
        Object[][] data = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy Sheet " + sheetName + " ở trong file " + filePath);
            }

            int rowCount = sheet.getLastRowNum();
            int colCount = sheet.getRow(0).getLastCellNum();
            data = new Object[rowCount][colCount];

            // Duyệt từ row = 1 vì row 0 thường là tiêu đề (Header row)
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    if (row == null) {
                        data[i - 1][j] = "";
                        continue;
                    }
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        data[i - 1][j] = "";
                    } else {
                        switch (cell.getCellType()) {
                            case STRING:
                                data[i - 1][j] = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                data[i - 1][j] = String.valueOf(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                data[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                data[i - 1][j] = cell.getCellFormula();
                                break;
                            default:
                                data[i - 1][j] = ""; // Bao gồm CellType.BLANK, _NONE
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}

